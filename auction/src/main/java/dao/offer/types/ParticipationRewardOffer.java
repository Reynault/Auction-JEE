package dao.offer.types;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import model.Auction;
import model.User;

public class ParticipationRewardOffer extends Offer {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    private final double threshold;
    private final int minimalNumberOfParticipation;
    private final double amountToReduce;

    public ParticipationRewardOffer(double threshold, int minimalNumberOfParticipation, double amountToReduce) {
        this.threshold = threshold;
        this.minimalNumberOfParticipation = minimalNumberOfParticipation;
        this.amountToReduce = amountToReduce;
    }

    @Override
    public double applyOffer(User u, Article a) {
        Auction au = a.getAuction();
        double best = au.getBestPrice();

        if (best >= threshold) {
            String request = "SELECT COUNT(p.id) FROM Participation p WHERE p.bidder.login = :login";
            Query query = em.createQuery(request);
            query.setParameter("login", u.getLogin());
            int nbParticipations = (int) query.getSingleResult();

            if (nbParticipations >= minimalNumberOfParticipation) {
                return isGreater(best, amountToReduce);
            } else {
                return best;
            }
        } else {
            return best;
        }
    }

    @Override
    public String description() {
        return "Réduction de " + amountToReduce + " si la valeur est supérieure à " + threshold
                + " si votre nombre minimal de participation total est supérieur à " + minimalNumberOfParticipation;
    }
}
