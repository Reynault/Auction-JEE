package dao.offer.types;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Article;
import model.Auction;
import model.Parameter;
import model.User;

public class ParticipationRewardOffer extends Offer {

    public ParticipationRewardOffer() {
        super(3);
    }

    @Override
    public double compute(EntityManager em, User u, Article a, double price, List<Parameter> params) {
        Auction au = a.getAuction();
        double best = au.getBestPrice();

        if (best >= params.get(0).getParameterValue()) {
            String request = "SELECT COUNT(p.id) FROM Participation p WHERE p.bidder.login = :login";
            Query query = em.createQuery(request);
            query.setParameter("login", u.getLogin());
            Long nbParticipations = (Long) query.getSingleResult();

            if (nbParticipations >= params.get(2).getParameterValue()) {
                return isGreater(price, params.get(1).getParameterValue());
            } else {
                return price;
            }
        } else {
            return price;
        }
    }
}
