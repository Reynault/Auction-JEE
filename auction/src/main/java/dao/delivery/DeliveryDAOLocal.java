package dao.delivery;

import java.util.Collection;
import javax.ejb.Local;
import model.Address;
import model.Article;
import model.Delivery;
import model.User;

@Local
public interface DeliveryDAOLocal {

    Delivery initializeDelivery(Address address, double price, User user, Article article);

    Collection<Delivery> getDeliveries(String login);

    Delivery getOneDelivery(String login, long id);

    boolean hasDelivery(String login, long id);

    void validateDelivery(Delivery d);
}
