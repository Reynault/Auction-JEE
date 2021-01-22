package com.ul.springauction.services;

import com.ul.springauction.DAO.repository.ParticipationRepository;
import com.ul.springauction.services.validator.DtoValidator;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.dto.NewParticipation;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Auction;
import model.Participation;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.ErrorMessageManager;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Service gestionnaire des participations
 */
@Service
public class ParticipationService {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private DtoValidator dtoValidator;


    // Compte le nombre de participation de l'utilisateur à l'enchère
    public Long countParticipation(User u){
        return participationRepository.countAllByBidder(u);
    }


    // Fait participer l'utilisateur à l'enchère
    public Participation participate(String token, NewParticipation participation, long id) throws BadRequestException {
        dtoValidator.validation(participation);
        Participation p;
        Article a = articleService.findById(id);
        User u = userService.findUser(token);
        // Si de mauvaises informations sont données
        if (a == null || u == null){
            throw new BadRequestException(ErrorMessageManager.MISSING_DATA);
        } else {
            Auction auc = a.getAuction();
            // Si il a bien enchère
            if(auc == null || auc.getTimeLimit().before(Date.from(Instant.now()))){
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            } else {
                // Si l'utilisateur n'est pas propriétaire de l'article
                if (u.getSold().contains(a)) {
                    throw new BadRequestException(ErrorMessageManager.USER_OWN);
                } else {
                    // Si la valeur de la participation n'est pas trop petite
                    if(participation.getValue() < auc.getFirstPrice()){
                        throw new BadRequestException(ErrorMessageManager.BIGGER_VALUE);
                    } else {
                        p = new Participation(participation.getValue(), u);
                        auc.addParticipation(p);
                        if (p.getPrice() > auc.getBestPrice()) auc.setBest(p);
                        a.setAuction(auc);
                        articleService.saveUpdatedArticle(a);
                    }
                }
            }
            return p;
        }
    }


    // Cherche les articles où a participer l'utilisateur encore en cours ou s'il est le meilleur
    public List<Article> getInfoAllParticipation(String token){
        User u = userService.findUser(token);
        List<Participation> participations = participationRepository.findAllByBidder(u);
        List<Auction> auctions = auctionService.findAuctionsByParticipations(participations);
        return articleService.findArticlesByAuctions(auctions);
    }


    // Cherche un article où à participer
    public Article getInfoOneParticipation(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article a = articleService.findById(id);
        // Si les valeurs sont bien données
        if (u == null || a == null){
            throw new BadRequestException(ErrorMessageManager.MISSING_DATA);
        } else {
            Auction auc = a.getAuction();
            // Si il y a bien enchère
            if(auc == null || auc.getTimeLimit().before(Date.from(Instant.now()))) {
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            } else {
                if (u.getSold().contains(a)){
                    throw new BadRequestException(ErrorMessageManager.USER_OWN);
                } else {
                    List<Participation> participations = auc.getParticipations();
                    boolean isBidder = hasBid(participations, u);
                    // Si l'utilisateur a participé
                    if (isBidder) {
                        return a;
                    } else {
                        throw new BadRequestException(ErrorMessageManager.USER_NOT_A_BIDDER);
                    }
                }
            }
        }
    }


    // Regarde si l'utilisateur a participer aux enchères
    public boolean hasBid(List<Participation> participations, User u){
        boolean bidder = false;
        for(Participation p : participations){
            if (p.getBidder() == u){
                bidder = true;
                break;
            }
        }
        return bidder;
    }
}
