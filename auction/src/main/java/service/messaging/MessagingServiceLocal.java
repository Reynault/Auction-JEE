package service.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.ejb.Local;

@Local
public interface MessagingServiceLocal {

    final static String PENDING_DELIVERIES = "pending";
    final static String VALIDATED_DELIVERIES = "validated";

    void sendMessage(byte[] message, String queueName) throws IOException, TimeoutException;
}
