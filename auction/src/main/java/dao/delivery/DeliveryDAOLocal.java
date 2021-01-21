package dao.delivery;

import java.util.Collection;
import javax.ejb.Local;
import model.Delivery;
import shared.dto.UserAddress;

@Local
public interface DeliveryDAOLocal {

    Delivery initializeDelivery(UserAddress address, double price, String login, long id);

    Collection<Delivery> getDeliveries(String login);

    Delivery getOneDelivery(String login, long id);

    boolean hasDelivery(String login, long id);

    void validateDelivery(Delivery d);
}
