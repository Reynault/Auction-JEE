package service.messaging.reveiver;

import dao.delivery.DeliveryDAOLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import model.Delivery;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "acknowledgeMode",
            propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
            propertyName = "destination",
            propertyValue = "ValidatedDeliveries")
})
public class DeliveryReceiver implements MessageListener {

    @EJB
    private DeliveryDAOLocal deliveryDao;

    @Override
    public void onMessage(Message msg) {
        receiveMessage(msg);
    }

    private void receiveMessage(Message msg) {
        try {
            Delivery d = msg.getBody(Delivery.class);
            System.out.println("---------------VALIDATED DELIVERY " + d.getId() + " RECEIVED-------------------");
            deliveryDao.validateDelivery(d);
        } catch (JMSException ex) {
            Logger.getLogger(DeliveryReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
