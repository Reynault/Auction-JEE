package service;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Delivery;
import singleton.DeliveryManager;

@Stateless
public class DeliveryService implements DeliveryServiceLocal {

    @EJB
    private DeliveryManager manager;

    public Collection<Delivery> getPendingDeliveries() {
        return manager.getTreatedDeliveries();
    }
}
