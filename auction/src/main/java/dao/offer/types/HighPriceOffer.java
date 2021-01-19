package dao.offer.types;

import java.util.List;
import javax.persistence.EntityManager;
import model.Article;
import model.Parameter;
import model.User;

public class HighPriceOffer extends Offer {

    public HighPriceOffer() {
        super(2);
    }

    @Override
    public double compute(EntityManager em, User u, Article a, double price, List<Parameter> params) {
        double best = a.getAuction().getBestPrice();
        System.out.println("V : " + params.get(0).getParameterValue());
        System.out.println("V : " + params.get(1).getParameterValue());
        if (best >= params.get(0).getParameterValue()) {
            System.out.println("APPLICATION H");
            return isGreater(price, params.get(1).getParameterValue());
        } else {
            return price;
        }
    }

}
