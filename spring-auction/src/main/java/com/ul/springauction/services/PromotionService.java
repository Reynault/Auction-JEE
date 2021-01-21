package com.ul.springauction.services;

import com.ul.springauction.DAO.offer.Offer;
import com.ul.springauction.DAO.repository.PromotionRepository;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.PriceResponse;
import com.ul.springauction.shared.response.Response;
import model.Article;
import model.Parameter;
import model.Promotion;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotion(){
        List<Promotion> promotions = promotionRepository.findAll();
        promotions.removeIf(p -> !p.isDaily());
        return promotions;
    }

    public List<Promotion> getPromotionByList(List<Long> promos) throws BadRequestException {
        List<Promotion> promotionList = new ArrayList<>();
        Promotion p;
        for (long l : promos){
            p = promotionRepository.findById(l);
            if (p == null){
                throw new BadRequestException("Une des promotions n'existe pas");
            }
            promotionList.add(p);
        }
        if(promotionList.isEmpty()){
            throw new BadRequestException("Il n'y a aucunes promotions donnees valide");
        } else {
            return promotionList;
        }
    }

    public Response calcuNewPrice(String token, long id, List<Long> promos) throws BadRequestException {
        Article a = articleService.findById(id);
        User u = userService.findUser(token);
        if (a.getAuction() == null){
            throw new BadRequestException("L'article n'est pas en vente");
        } else {
            if (participationService.hasBid(a.getAuction().getParticipations(), u)){
                if (promos == null) {
                    return new PriceResponse(a.getAuction().getBestPrice());
                } else {
                    List<Promotion> promotions = getPromotionByList(promos);
                    double finalPrice = a.getAuction().getBestPrice();
                    Offer o;
                    for(Promotion p : promotions){
                        List<Parameter> parameters = p.getParameters();
                        o = Offer.createOffer(p.getType());
                        finalPrice = o.applyOffer(participationService, u, a, finalPrice, parameters);
                    }
                    return new PriceResponse(finalPrice);
                }
            } else {
                throw new BadRequestException("L'utilisateur n'a pas participer");
            }
        }
    }
}
