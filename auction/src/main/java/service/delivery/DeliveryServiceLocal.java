package service.delivery;

import java.util.Collection;
import javax.ejb.Local;
import model.Delivery;
import shared.dto.UserAddress;
import shared.params.PromoParams;

@Local
public interface DeliveryServiceLocal {

    Delivery deliver(UserAddress address, PromoParams params, String login, long id);

    Collection<Delivery> getDeliveries(String login);

    Delivery getOneDelivery(String login, long id);
}
