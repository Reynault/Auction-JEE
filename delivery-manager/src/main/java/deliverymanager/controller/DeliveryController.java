package deliverymanager.controller;

import deliverymanager.service.delivery.DeliveryService;
import model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/pending")
    public Collection<Delivery> getPendingDeliveries(){
        return deliveryService.getPendingDeliveries();
    }
}
