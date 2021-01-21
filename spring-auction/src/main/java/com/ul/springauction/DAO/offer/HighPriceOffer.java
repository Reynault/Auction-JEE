package com.ul.springauction.DAO.offer;

import com.ul.springauction.services.ParticipationService;
import model.Article;
import model.Parameter;
import model.User;

import java.util.List;

public class HighPriceOffer extends Offer{

    public HighPriceOffer() {
        super(2);
    }

    @Override
    public double applyOffer(ParticipationService service, User u, Article a, double price, List<Parameter> param) {
        if (a.getAuction().getBestPrice() >= param.get(0).getParameterValue()){
            return isBigger(price, param.get(1).getParameterValue());
        } else {
            return price;
        }
    }
}
