package com.ul.springauction.services;

import com.ul.springauction.DAO.repository.AuctionRepository;
import com.ul.springauction.shared.dto.AuctionAdd;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Auction;
import model.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.ErrorMessageManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service en charge des enchères
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;


    // Créé une enchère et la retourne si tout est correcte
    public Auction createAuction(AuctionAdd auction, Article a) throws BadRequestException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(auction.getTimeLimit());
        } catch (ParseException e) {
            throw new BadRequestException(ErrorMessageManager.BAD_DATE_FORMAT);
        }
        // La date de fin de l'enchère doit forcément être après la date actuelle
        if (!d.after(Date.from(Instant.now()))){
            throw new BadRequestException(ErrorMessageManager.DATE_NOT_IN_THE_FUTUR);
        } else {
            return new Auction(auction.getFirstPrice(), d, null, new ArrayList<>(), a);
        }
    }


    // Supprime une enchère
    public void deleteAuction(Auction a){
        auctionRepository.delete(a);
    }


    // Cherche une liste d'enchères en donnant une liste de participations
    public List<Auction> findAuctionsByParticipations(List<Participation> participations){
        List<Auction> auctions = new ArrayList<>();
        for(Participation p : participations){
            Auction a = auctionRepository.findByParticipationsContaining(p);
            // Si l'enchère est encore en cours ou bien s'il est le meilleur
            if (a.getTimeLimit().after(Date.from(Instant.now())) || a.getBest() == p){
                auctions.add(a);
            }
        }
        return auctions;
    }
}
