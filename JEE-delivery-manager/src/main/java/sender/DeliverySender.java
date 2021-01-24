package sender;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import model.Delivery;
import singleton.DeliveryManager;

@Stateless
public class DeliverySender implements DeliverySenderLocal {

    @Inject
    JMSContext context;

    @Resource(lookup = "ValidatedDeliveries")
    Destination orderQueue;

    @EJB
    private DeliveryManager manager;

    @Override
    public void send(Delivery d) {
        if (manager.removeValue(d)) {
            JMSProducer producer = context.createProducer();
            producer.send(orderQueue, d);
            System.out.println("---------------DELIVERY " + d.getId() + " SENT-------------------");
        }
    }
}
