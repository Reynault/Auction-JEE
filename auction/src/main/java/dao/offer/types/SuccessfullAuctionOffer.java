package dao.offer.types;

import model.Article;
import model.Auction;
import model.User;

public class SuccessfullAuctionOffer extends Offer {

    private final double threshold;
    private final int minimalNumberOfPerson;
    private final double amountToReduce;

    public SuccessfullAuctionOffer(double threshold, int minimalNumberOfPerson, double amountToReduce) {
        this.threshold = threshold;
        this.minimalNumberOfPerson = minimalNumberOfPerson;
        this.amountToReduce = amountToReduce;
    }

    @Override
    public double applyOffer(User u, Article a) {
        Auction au = a.getAuction();
        double best = au.getBestPrice();
        if (au.getParticipations().size() > minimalNumberOfPerson && best >= threshold) {
            return isGreater(best, amountToReduce);
        } else {
            return best;
        }
    }

    @Override
    public String description() {
        return "R�duction de " + amountToReduce + " si la valeur est sup�rieure � " + threshold + " et si l'ench�re poss�de plus de " + minimalNumberOfPerson + " participants";
    }
}
