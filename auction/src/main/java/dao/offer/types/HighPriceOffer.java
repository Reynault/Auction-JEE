package dao.offer.types;

import model.Article;
import model.User;

public class HighPriceOffer extends Offer {

    private final double threshold;
    private final double amountToReduce;

    public HighPriceOffer(double threshold, double amountToReduce) {
        this.threshold = threshold;
        this.amountToReduce = amountToReduce;
    }

    @Override
    public double applyOffer(User u, Article a) {
        double best = a.getAuction().getBestPrice();
        if (best >= threshold) {
            return isGreater(best, amountToReduce);
        } else {
            return best;
        }
    }

    @Override
    public String description() {
        return "Réduction de " + amountToReduce + " si la valeur est supérieure à " + threshold;
    }

}
