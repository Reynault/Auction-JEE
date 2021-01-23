package com.ul.springauction.DAO.offer;

import com.ul.springauction.services.ParticipationService;
import enumeration.PromotionType;
import model.Article;
import model.Parameter;
import model.User;

import java.util.List;

/**
 * Classe abstraite d'une offre de réduction
 */
public abstract class Offer {

    private int nbParam;

    public Offer(int nbParam){
        this.nbParam = nbParam;
    }

    public static Offer createOffer(PromotionType type){
        Offer o;
        switch (type){
            case SUCCESSFULL_AUCTION:
                o = new SuccessfullAuctionOffer();
                break;
            case PARTICIPATION_REWARD:
                o = new ParticipationRewardOffer();
                break;
            default:
                o = new HighPriceOffer();
                break;
        }
        return o;
    }

    public double isBigger(double best, double amountToReduce){
        return (best - amountToReduce) > 0 ? (best - amountToReduce) : 0;
    }

    public abstract double applyOffer(ParticipationService service, User u, Article a, double price, List<Parameter> parameters);

    public int getNbParam() {
        return nbParam;
    }

    public void setNbParam(int nbParam) {
        this.nbParam = nbParam;
    }
}
