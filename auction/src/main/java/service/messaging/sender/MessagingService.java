package service.messaging.sender;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import service.messaging.ConnectionManager;

@Stateless
public class MessagingService implements MessagingServiceLocal {

    @EJB
    private ConnectionManager manager;

    private Channel channel;

    @PostConstruct
    public void init() {
        try {
            channel = manager.getChannel();
        } catch (IOException ex) {
            Logger.getLogger(MessagingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void delete() {
        try {
            channel.close();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(MessagingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendMessage(byte[] message, String queueName) throws IOException, TimeoutException {
        channel.basicPublish(
                "",
                queueName,
                null,
                message
        );
    }

}
