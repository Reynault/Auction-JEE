package com.ul.springauction.services;

import com.ul.springauction.DAO.ParticipationRepository;
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

import java.time.Instant;
import java.util.Date;
import java.util.List;

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

    public Participation participate(String token, NewParticipation participation, long id) throws BadRequestException {
        dtoValidator.validation(participation);
        Participation p;
        Article a = articleService.findById(id);
        if(a.getAuction() == null){
            throw new BadRequestException("Aucune enchere disponible pour cet article");
        } else {
            Auction auc = a.getAuction();
            if (auc.getTimeLimit().before(Date.from(Instant.now())) || a.isHasBeenSold()) {
                throw new BadRequestException("Il n'est plus possible de participer aux encheres pour cet article");
            } else {
                User u = userService.findUser(token);
                if (u.getSold().contains(a)) {
                    throw new BadRequestException("Impossible de participer aux encheres de votre propre article");
                } else {
                    if(participation.getValue() < auc.getFirstPrice()){
                        throw new BadRequestException("Impossible de proposer un prix de moins de "+auc.getFirstPrice()+" €");
                    } else {
                        p = new Participation(participation.getValue(), u);
                        auc.addParticipation(p);
                        if (p.getPrice() > auc.getBestPrice()) auc.setBest(p);
                        a.setAuction(auc);
                        articleService.saveUpdatedArticle(a);
                    }
                }
            }
        }
        return p;
    }

    public List<Article> getInfoAllParticipation(String token){
        User u = userService.findUser(token);
        List<Participation> participations = participationRepository.findAllByBidder(u);
        List<Auction> auctions = auctionService.findAuctionsByParticipations(participations);
        return articleService.findArticlesByAuctions(auctions);
    }

    public Article getInfoOneParticipation(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article a = articleService.findById(id);
        Auction auc = a.getAuction();
        if(auc == null || a.isHasBeenSold()){
            throw new BadRequestException("Aucune enchere pour cet article ou il a ete vendu");
        } else {
            List<Participation> participations = auc.getParticipations();
            boolean isBidder = hasBid(participations, u);
            if (isBidder){
                return a;
            } else {
                throw new BadRequestException("L'utilisateur n'a pas participer aux encheres de l'article");
            }
        }
    }

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
