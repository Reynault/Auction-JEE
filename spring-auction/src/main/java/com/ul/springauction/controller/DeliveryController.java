package com.ul.springauction.controller;

import com.ul.springauction.configuration.Authentificated;
import com.ul.springauction.services.DeliveryService;
import com.ul.springauction.shared.dto.RegisterAddress;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/auction/participation")
@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping(value = "/{id}/deliver")
    @Authentificated
    public Delivery buyArticle(@RequestHeader(value = "Authorization") String token,@PathVariable long id, @RequestParam(required = false)List<Long> o, @RequestBody RegisterAddress address) throws BadRequestException {
        return deliveryService.buyArticle(token, id, o, address);
    }

    @GetMapping(value = "/deliveries")
    @Authentificated
    public List<Delivery> showDeliveries(@RequestHeader(value = "Authorization") String token) throws BadRequestException {
        return deliveryService.showAllDeliveries(token);
    }

    @GetMapping(value = "/{id}/deliveries")
    @Authentificated
    public Delivery showOneDelivery(@RequestHeader(value = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        return deliveryService.showOneDelivery(token, id);
    }
}
