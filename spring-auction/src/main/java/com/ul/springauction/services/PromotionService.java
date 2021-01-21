package com.ul.springauction.services;

import com.ul.springauction.DAO.PromotionRepository;
import model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotion(){
        List<Promotion> promotions = promotionRepository.findAll();
        promotions.removeIf(p -> !p.isDaily());
        return promotions;
    }
}
