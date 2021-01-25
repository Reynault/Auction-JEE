package service.messaging.sender;

import javax.ejb.Local;
import model.Delivery;

@Local
public interface DeliverySenderLocal {

    void send(Delivery d);
}
