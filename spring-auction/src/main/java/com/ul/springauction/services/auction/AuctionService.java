package com.ul.springauction.services.auction;

import com.ul.springauction.DAO.AuctionRepository;
import com.ul.springauction.shared.dto.AuctionAdd;
import model.Article;
import model.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;


    public Auction createAuction(AuctionAdd auction, Article a){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(auction.getTimeLimit());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Auction(auction.getFirstPrice(), d, null, new ArrayList<>(), a);
    }

    public void deleteAuction(Auction a){
        auctionRepository.delete(a);
    }
}
