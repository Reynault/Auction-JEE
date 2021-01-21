package service.messaging.reveiver;

import com.rabbitmq.client.DeliverCallback;
import javax.ejb.Local;

@Local
public interface MyDeliveryCallbackLocal extends DeliverCallback {

}
