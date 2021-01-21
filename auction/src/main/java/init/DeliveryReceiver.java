package init;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import service.messaging.ConnectionManager;
import service.messaging.RessourceManager;
import service.messaging.reveiver.MyDeliveryCallbackLocal;

@Singleton
@Startup
@DependsOn("OfferManager")
public class DeliveryReceiver {

    @EJB
    private MyDeliveryCallbackLocal deliveryCallback;

    @EJB
    private ConnectionManager manager;

    private Channel channel;

    @PostConstruct
    public void init() {
        try {
            channel = manager.getChannel();
            receiveDelivery();
        } catch (IOException ex) {
            Logger.getLogger(DeliveryReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void delete() {
        try {
            channel.close();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(DeliveryReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveDelivery() {
        try {
            System.out.println("----Starting connection to queue----");
            channel.basicConsume(
                    RessourceManager.VALIDATED_DELIVERIES,
                    true,
                    deliveryCallback,
                    consumerTag -> {

                    }
            );
            System.out.println("----Connection to queue done----");
        } catch (IOException ex) {
            Logger.getLogger(DeliveryReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
