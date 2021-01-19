package dao.offer.types;

import java.util.List;
import javax.persistence.EntityManager;
import model.Article;
import model.Auction;
import model.Parameter;
import model.User;

public class SuccessfullAuctionOffer extends Offer {

    public SuccessfullAuctionOffer() {
        super(3);
    }

    @Override
    public double compute(EntityManager em, User u, Article a, double price, List<Parameter> params) {
        Auction au = a.getAuction();
        double best = au.getBestPrice();
        System.out.println("COUNT " + au.getParticipations().size());
        if (au.getParticipations().size() >= params.get(2).getParameterValue() && best >= params.get(0).getParameterValue()) {
            System.out.println("APPLICATION S");
            return isGreater(price, params.get(1).getParameterValue());
        } else {
            return price;
        }
    }

}
