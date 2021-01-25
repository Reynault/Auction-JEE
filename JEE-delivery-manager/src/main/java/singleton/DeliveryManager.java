package singleton;

import java.util.Collection;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import model.Delivery;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class DeliveryManager {

    private HashMap<Long, Delivery> treated_deliveries;

    @PostConstruct
    public void init() {
        this.treated_deliveries = new HashMap<>();
    }

    @Lock(LockType.WRITE)
    public boolean putValue(Delivery d) {
        if (treated_deliveries.containsKey(d.getId())) {
            return false;
        } else {
            this.treated_deliveries.put(d.getId(), d);
            return true;
        }
    }

    @Lock(LockType.WRITE)
    public boolean removeValue(Delivery d) {
        if (treated_deliveries.containsKey(d.getId())) {
            this.treated_deliveries.remove(d.getId());
            return true;
        } else {
            return false;
        }
    }

    @Lock(LockType.READ)
    public Collection<Delivery> getTreatedDeliveries() {
        return treated_deliveries.values();
    }
}
