package service.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MessagingService implements MessagingServiceLocal {

    @EJB
    private ConnectionManager manager;

    @Override
    public void sendMessage(byte[] message, String queueName) throws IOException, TimeoutException {
        Connection c = manager.getConnection();
        Channel channel = c.createChannel();

        channel.basicPublish(
                "",
                queueName,
                null,
                message
        );

        c.close();
    }

}
