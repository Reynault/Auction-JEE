package com.ul.springauction.services;

import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Auction;
import model.Delivery;
import model.User;
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

    public Delivery buyArticle(String token, long id, List<Long> param) throws BadRequestException {
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
                        return null;
                    }
                }
            } else {
                throw new BadRequestException("L'article n'a pas ete en vente ou est deja vendu");
            }
        }
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
        throw new BadRequestException("L'article est introuvable");
    }
}
