package com.ul.springauction.DAO.offer;

import com.ul.springauction.services.ParticipationService;
import model.Article;
import model.Parameter;
import model.User;

import java.util.List;

public class SuccessfullAuctionOffer extends Offer{

    public SuccessfullAuctionOffer() {
        super(3);
    }

    @Override
    public double applyOffer(ParticipationService service, User u, Article a, double price, List<Parameter> parameters) {
        if (a.getAuction().getParticipations().size() >= parameters.get(2).getParameterValue() && a.getAuction().getBestPrice() >= parameters.get(0).getParameterValue()){
            return isBigger(price, parameters.get(1).getParameterValue());
        } else {
            return price;
        }
    }
}
