package service;

import java.util.Collection;
import javax.ejb.Local;
import model.Delivery;

@Local
public interface DeliveryServiceLocal {

    Collection<Delivery> getPendingDeliveries();
}
