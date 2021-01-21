package com.ul.springauction.DAO.offer;

import com.ul.springauction.services.ParticipationService;
import model.Article;
import model.Parameter;
import model.User;

import java.util.List;

public class ParticipationRewardOffer extends Offer{

    public ParticipationRewardOffer() {
        super(3);
    }

    @Override
    public double applyOffer(ParticipationService service, User u, Article a, double price, List<Parameter> parameters) {
        if (a.getAuction().getBestPrice() >= parameters.get(0).getParameterValue()) {
            Long nbParticipation = service.countParticipation(u);

            if (nbParticipation >= parameters.get(2).getParameterValue()){
                return isBigger(price, parameters.get(1).getParameterValue());
            } else {
                return price;
            }
        } else {
            return price;
        }
    }
}
