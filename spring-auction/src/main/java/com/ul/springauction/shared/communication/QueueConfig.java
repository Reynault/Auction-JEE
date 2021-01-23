package com.ul.springauction.shared.communication;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public final static String PENDING_DELIVERIES = "pending";
    public final static String VALIDATED_DELIVERIES = "validated";

    @Bean
    public Queue pending() {
        return new Queue(PENDING_DELIVERIES);
    }

    @Bean
    public Queue validated() {
        return new Queue(VALIDATED_DELIVERIES);
    }
}
