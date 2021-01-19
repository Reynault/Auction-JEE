package dao.offer.types;

import enumeration.PromotionType;
import java.util.List;
import javax.persistence.EntityManager;
import model.Article;
import model.Parameter;
import model.User;

public abstract class Offer {

    private int nbParams;

    public static Offer createOffer(PromotionType type) {
        Offer o;
        switch (type) {
            case HIGH_PRICE:
                o = new HighPriceOffer();
                break;
            case PARTICIPATION_REWARD:
                o = new ParticipationRewardOffer();
                break;
            case SUCCESSFULL_AUCTION:
                o = new SuccessfullAuctionOffer();
                break;
            default:
                o = new HighPriceOffer();
                break;
        }
        return o;
    }

    public Offer(int nbParams) {
        this.nbParams = nbParams;
    }

    public double isGreater(double best, double amountToReduce) {
        return (best - amountToReduce) > 0 ? (best - amountToReduce) : 0;
    }

    public double applyOffer(EntityManager em, User u, Article a, double price, List<Parameter> params) {
        if (params.size() >= nbParams) {
            return compute(em, u, a, price, params);
        } else {
            return price;
        }
    }

    protected abstract double compute(EntityManager em, User u, Article a, double price, List<Parameter> params);

}
