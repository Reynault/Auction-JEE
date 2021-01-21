package service.messaging.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.ejb.Local;

@Local
public interface MessagingServiceLocal {

    void sendMessage(byte[] message, String queueName) throws IOException, TimeoutException;

}
