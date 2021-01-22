package com.ul.springauction.shared.scheduling;

import com.ul.springauction.DAO.repository.PromotionRepository;
import model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * Component qui s'active tout les jours à minuit grâce au @Scheduled
 * Met à jour les promotions du jour
 */
@Component
public class PromotionRotation {

    @Autowired
    private PromotionRepository promotionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void changeDailyOffers(){
        Random r = new Random();
        List<Promotion> promotionList = promotionRepository.findAllByDaily(true);
        for (Promotion p : promotionList){
            p.setDaily(false);
            promotionRepository.save(p);
        }
        List<Promotion> allPromos = promotionRepository.findAll();
        int nbDaily = r.nextInt(allPromos.size());
        int n = 0;
        for (int i = 0; i < nbDaily; i++){
            n = r.nextInt(allPromos.size());
            allPromos.remove(n);
        }
        for (Promotion p : allPromos){
            p.setDaily(true);
            promotionRepository.save(p);
        }
    }
}
