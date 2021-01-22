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

import java.time.Instant;
import java.util.Date;
import java.util.List;

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

    public Delivery buyArticle(String token, long id, List<Long> param,  RegisterAddress address) throws BadRequestException {
        dtoValidator.validation(address);
        User u = userService.findUser(token);
        Article a = articleService.findById(id);
        if(a == null){
            throw new BadRequestException("L'article voulut n'est pas bon");
        } else {
            if (a.getAuction() != null && !a.isHasBeenSold()){
                if(!a.getAuction().getTimeLimit().before(Date.from(Instant.now()))){
                    throw new BadRequestException("L'article est encore en vente");
                } else {
                    Auction auc = a.getAuction();
                    if (!participationService.hasBid(auc.getParticipations(), u)){
                        throw new BadRequestException("L'utilisateur n'a pas participer");
                    } else {
                        Address newAddress = RegisterAddress.convertToAddress(address);
                        if (newAddress == null && u.getHome() == null){
                            throw new BadRequestException("L'utilisateur n'a aucune addresse de livraison");
                        } else {
                            u.setHome(newAddress);
                            List<Promotion> promotions = promotionService.getPromotionByList(param);
                            double finalPrice = a.getAuction().getBestPrice();
                            Offer o;
                            for(Promotion p : promotions){
                                List<Parameter> parameters = p.getParameters();
                                o = Offer.createOffer(p.getType());
                                finalPrice = o.applyOffer(participationService, u, a, finalPrice, parameters);
                            }
                            Delivery d = createAndAddDeliveryToUser(finalPrice, a, u);
                            // ajout a la queue a faire
                            return d;
                        }
                    }
                }
            } else {
                throw new BadRequestException("L'article n'a pas ete en vente ou est deja vendu");
            }
        }
    }

    public Delivery createAndAddDeliveryToUser(double price, Article a, User u){
        ArticleToDeliver article = new ArticleToDeliver(a.getName(), a.getDescription());
        Delivery d = new Delivery(DeliveryStep.IN_PROCESS, price, u.getHome(), article);
        u.getDeliveries().add(d);
        userService.saveUpdatedUser(u);
        a.setHasBeenSold(true);
        articleService.saveUpdatedArticle(a);
        return d;
    }

    public List<Delivery> showAllDeliveries(String token){
        User u = userService.findUser(token);
        return u.getDeliveries();
    }

    public Delivery showOneDelivery(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        for (Delivery d : u.getDeliveries()){
            if(d.getId() == id){
                return d;
            }
        }
        throw new BadRequestException("La commande est introuvable");
    }
}
