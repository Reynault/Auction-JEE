package deliverymanager.delivery.service.message;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Class that will receive delivery request from the auction application
 */
@RabbitListener(queues = "hello")
public class DeliveryReceiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println("---------------DELIVERY RECEIVED-------------------");
    }
}
