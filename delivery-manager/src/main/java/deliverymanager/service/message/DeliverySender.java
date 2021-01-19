package deliverymanager.delivery.service.message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class that will send validated
 */
@Service
public class DeliverySender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send() {
        System.out.println("test 1 2 1 2");
        String message = "Hello World!";
        this.template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
