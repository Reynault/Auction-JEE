package com.ul.springauction.controller;

import com.ul.springauction.services.PromotionService;
import model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/auction/participation")
@RestController
@CrossOrigin
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping(value = "/promotions")
    public List<Promotion> getAllPromotions(){
        return promotionService.getAllPromotion();
    }
}
