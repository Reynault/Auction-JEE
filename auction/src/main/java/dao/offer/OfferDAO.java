package dao.offer;

import dao.auth.UserDAOLocal;
import dao.offer.types.Offer;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import model.Parameter;
import model.Promotion;
import model.User;
import shared.params.PromoParams;

@Stateless
public class OfferDAO implements OfferDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private UserDAOLocal user;

    @Override
    public Collection<Promotion> getDailyPromotions() {
        Query q = em.createNamedQuery("Promotion.getDaily");
        return (Collection<Promotion>) q.getResultList();
    }

    @Override
    public boolean checkIfExists(Collection<Long> promotions) {
        for (Long l : promotions) {
            Query q = em.createNamedQuery("Promotion.verifyDaily");
            q.setParameter("id", l);
            if ((Long) q.getSingleResult() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double checkPrice(User user, Article article, PromoParams p) {
        Promotion promo;
        List<Long> promotions = p.getPromotions();
        List<Parameter> params;
        Offer o;
        Query q;
        double reducedPrice = article.getAuction().getBestPrice();

        for (Long l : promotions) {
            promo = em.find(Promotion.class, l);
            if (promo != null) {
                q = em.createNamedQuery("Promotion.getOrderedParams");
                q.setParameter("id", l);
                params = (List<Parameter>) q.getResultList();
                o = Offer.createOffer(promo.getType());
                reducedPrice = o.applyOffer(em, user, article, reducedPrice, params);
            }
        }

        return reducedPrice;
    }

}
