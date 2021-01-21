package deliverymanager.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deliverymanager.config.QueueConfig;
import deliverymanager.service.delivery.DeliveryManager;
import model.Delivery;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Class that will send validated
 */
@Service
public class DeliverySender {
    private final RabbitTemplate template;
    private final DeliveryManager manager;
    private final Queue queue;

    @Autowired
    public DeliverySender(DeliveryManager manager, RabbitTemplate template, @Qualifier(QueueConfig.VALIDATED_DELIVERIES) Queue queue) {
        this.template = template;
        this.manager = manager;
        this.queue = queue;
    }

    public void send(Delivery d) {
        try {
            if (manager.removeValue(d)) {
                ObjectMapper mapper = new ObjectMapper();
                this.template.convertAndSend(queue.getName(), mapper.writeValueAsBytes(d));
                System.out.println("---------------DELIVERY " + d.getId() + " SENT-------------------");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
