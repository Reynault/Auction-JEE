package deliverymanager.service.delivery;

import model.Delivery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;

@Service
@Scope("singleton")
public class DeliveryManager {
    private final HashMap<Long, Delivery> treated_deliveries;

    public DeliveryManager() {
        this.treated_deliveries = new HashMap<>();
    }

    @Transactional
    public boolean putValue(Delivery d) {
        if(treated_deliveries.containsKey(d.getId())){
            return false;
        }else {
            this.treated_deliveries.put(d.getId(), d);
            return true;
        }
    }

    @Transactional
    public boolean removeValue(Delivery d) {
        if(treated_deliveries.containsKey(d.getId())){
            this.treated_deliveries.remove(d.getId());
            return true;
        }else {
            return false;
        }
    }

    public Collection<Delivery> getTreatedDeliveries() {
        return treated_deliveries.values();
    }
}
