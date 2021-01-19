package deliverymanager.delivery.controller;

import deliverymanager.delivery.service.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping(value = "/pending_deliveries")
    public Collection<String> getPendingDeliveries(){
        return deliveryService.getPendingDeliveries();
    }
}
