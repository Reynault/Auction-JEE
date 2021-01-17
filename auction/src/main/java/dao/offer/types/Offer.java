package dao.offer.types;

import model.Article;
import model.User;

public abstract class Offer {

    public double isGreater(double best, double amountToReduce) {
        return (best - amountToReduce) > 0 ? (best - amountToReduce) : 0;
    }

    public abstract double applyOffer(User u, Article a);

    public abstract String description();

}
