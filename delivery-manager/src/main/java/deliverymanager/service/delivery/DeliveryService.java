package deliverymanager.service.delivery;

import model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DeliveryService {
    private final DeliveryManager manager;

    @Autowired
    public DeliveryService(DeliveryManager manager) {
        this.manager = manager;
    }

    public Collection<Delivery> getPendingDeliveries() {
        return manager.getTreatedDeliveries();
    }
}
