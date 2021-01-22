package com.ul.springauction.services;

import com.ul.springauction.DAO.offer.Offer;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.services.validator.DtoValidator;
import com.ul.springauction.shared.dto.RegisterAddress;
import com.ul.springauction.shared.exception.BadRequestException;
import enumeration.DeliveryStep;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.ErrorMessageManager;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Service gestionnaire des commandes
 */
@Service
public class DeliveryService {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private DtoValidator dtoValidator;


    // Effectue la commande de l'article par l'utilisateur
    public Delivery buyArticle(String token, long id, List<Long> param,  RegisterAddress address) throws BadRequestException {
        dtoValidator.validation(address);
        User u = userService.findUser(token);
        Article a = articleService.findById(id);
        // Si tout est bien rentrée
        if(a == null || u == null){
            throw new BadRequestException(ErrorMessageManager.MISSING_DATA);
        } else {
            // Si l'article est bien en vente
            if (a.getAuction() != null && !a.isHasBeenSold()){
                // Que les enchères sont bien finies
                if(a.getAuction().getTimeLimit().after(Date.from(Instant.now()))){
                    throw new BadRequestException(ErrorMessageManager.AUCTION_ALWAYS_RUNNING);
                } else {
                    Auction auc = a.getAuction();
                    // L'utilisateur a bien participé
                    if (!participationService.hasBid(auc.getParticipations(), u)){
                        throw new BadRequestException(ErrorMessageManager.USER_NOT_A_BIDDER);
                    } else {
                        // L'utilisateur est le meilleur
                        if (auc.getBest().getBidder() == u) {
                            Address newAddress = RegisterAddress.convertToAddress(address);
                            // Si l'utilisateur a bien une addresse de livraison
                            if (newAddress == null && u.getHome() == null) {
                                throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ADDRESS);
                            } else {
                                u.setHome(newAddress);
                                List<Promotion> promotions = promotionService.getPromotionByList(param);
                                double finalPrice = a.getAuction().getBestPrice();
                                Offer o;
                                for (Promotion p : promotions) {
                                    List<Parameter> parameters = p.getParameters();
                                    o = Offer.createOffer(p.getType());
                                    finalPrice = o.applyOffer(participationService, u, a, finalPrice, parameters);
                                }
                                Delivery d = createAndAddDeliveryToUser(finalPrice, a, u);
                                // ajout à la queue a faire
                                return d;
                            }
                        } else {
                            throw new BadRequestException(ErrorMessageManager.USER_NOT_THE_BEST);
                        }
                    }
                }
            } else {
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL +" ou "+ErrorMessageManager.ALREADY_SOLD);
            }
        }
    }


    // Créé et enregistre dans la base de données la commande
    public Delivery createAndAddDeliveryToUser(double price, Article a, User u){
        ArticleToDeliver article = new ArticleToDeliver(a.getName(), a.getDescription());
        Delivery d = new Delivery(DeliveryStep.IN_PROCESS, price, u.getHome(), article);
        u.getDeliveries().add(d);
        userService.saveUpdatedUser(u);
        a.setHasBeenSold(true);
        articleService.saveUpdatedArticle(a);
        return d;
    }

    // Cherche les commandes de l'utilisateur
    public List<Delivery> showAllDeliveries(String token){
        User u = userService.findUser(token);
        return u.getDeliveries();
    }

    // Cherche une commande en particulier
    public Delivery showOneDelivery(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        for (Delivery d : u.getDeliveries()){
            if(d.getId() == id){
                return d;
            }
        }
        throw new BadRequestException(ErrorMessageManager.COMMAND_NOT_FOUND);
    }
}
