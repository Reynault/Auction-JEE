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
import shared.ErrorMessageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Service gestionnaire des promotions
 */
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


    // Cherche toutes les promotions du jour
    public List<Promotion> getAllPromotion(){
        List<Promotion> promotions = promotionRepository.findAll();
        if (promotions != null) {
            promotions.removeIf(p -> !p.isDaily());
        }
        return promotions;
    }


    // Cherche les promotions associées à leur id
    public List<Promotion> getPromotionByList(List<Long> promos) throws BadRequestException {
        List<Promotion> promotionList = new ArrayList<>();
        Promotion p;
        // On cherche les promos
        for (long l : promos) {
            p = promotionRepository.findById(l);
            // Si l'une voulue est introuvable par le repository
            if (p == null) {
                throw new BadRequestException(ErrorMessageManager.PROMOTION_NOT_FOUND);
            }
            promotionList.add(p);
        }
        return promotionList;
    }


    // Calcul le nouveau prix d'un article en vente après réduction pour aperçu
    public Response calcuNewPrice(String token, long id, List<Long> promos) throws BadRequestException {
        Article a = articleService.findById(id);
        User u = userService.findUser(token);
        // On vérifie que tout est bien rentrée
        if (a == null || u == null){
            throw new BadRequestException(ErrorMessageManager.MISSING_DATA);
        } else {
            // Qu'il y a bien une enchère
            if (a.getAuction() == null) {
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            } else {
                // Que l'utilisateur y a bien participé
                if (participationService.hasBid(a.getAuction().getParticipations(), u)) {
                    // S'il y a ou non des promos
                    if (promos == null) {
                        return new PriceResponse(a.getAuction().getBestPrice());
                    } else {
                        List<Promotion> promotions = getPromotionByList(promos);
                        double finalPrice = a.getAuction().getBestPrice();
                        Offer o;
                        for (Promotion p : promotions) {
                            List<Parameter> parameters = p.getParameters();
                            o = Offer.createOffer(p.getType());
                            finalPrice = o.applyOffer(participationService, u, a, finalPrice, parameters);
                        }
                        return new PriceResponse(finalPrice);
                    }
                } else {
                    throw new BadRequestException(ErrorMessageManager.USER_NOT_A_BIDDER);
                }
            }
        }
    }
}
