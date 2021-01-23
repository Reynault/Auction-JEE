package com.ul.springauction.shared.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Delivery;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Class that will receive delivery request from the auction application
 */
@Service
@RabbitListener(queues = QueueConfig.PENDING_DELIVERIES)
public class DeliveryReceiver {
    private final DeliveryManager manager;
    private final DeliverySender sender;

    @Autowired
    public DeliveryReceiver(DeliveryManager manager, DeliverySender sender) {
        this.manager = manager;
        this.sender = sender;
    }

    @RabbitHandler
    public void receive(byte[] in) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Delivery d = mapper.readValue(in, Delivery.class);
            System.out.println("---------------DELIVERY "+d.getId()+" RECEIVED-------------------");
            if(manager.putValue(d)){
                Thread.sleep(10000);
                sender.send(d);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
