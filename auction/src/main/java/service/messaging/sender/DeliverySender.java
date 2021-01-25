package service.messaging.sender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import model.Delivery;

@Stateless
public class DeliverySender implements DeliverySenderLocal {

    @Inject
    JMSContext context;

    @Resource(lookup = "PendingDeliveries")
    Destination orderQueue;

    @Override
    public void send(Delivery d) {
        JMSProducer producer = context.createProducer();
        producer.send(orderQueue, d);
        System.out.println("---------------PENDING DELIVERY " + d.getId() + " SENT-------------------");
    }
}
