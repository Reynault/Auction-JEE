package receiver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import model.Delivery;
import sender.DeliverySenderLocal;
import singleton.DeliveryManager;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "acknowledgeMode",
            propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
            propertyName = "destination",
            propertyValue = "PendingDeliveries")
})

public class DeliveryReceiver implements MessageListener {

    @EJB
    private DeliverySenderLocal sender;

    @EJB
    private DeliveryManager manager;

    @Override
    public void onMessage(Message msg) {
        receiveMessage(msg);
    }

    private void receiveMessage(Message msg) {
        try {
            Delivery d = msg.getBody(Delivery.class);
            System.out.println("---------------DELIVERY " + d.getId() + " RECEIVED-------------------");
            if (manager.putValue(d)) {
                Thread.sleep(10000);
                sender.send(d);
            }
        } catch (JMSException | InterruptedException ex) {
            Logger.getLogger(DeliveryReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
