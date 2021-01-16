package dao.auction;

import java.time.Instant;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Auction;

@Stateless
public class AuctionDAO implements AuctionDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public Auction isSold(long article_id) {
        Query query = em.createNamedQuery("Auction.findOne", Auction.class);
        query.setParameter("id", article_id);
        try {
            return (Auction) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean isFinished(long article_id) {
        Query query = em.createNamedQuery("Auction.findOne", Auction.class);
        query.setParameter("id", article_id);
        try {
            Auction a = (Auction) query.getSingleResult();
            return a.getTimeLimit().before(Date.from(Instant.now()));
        } catch (NoResultException e) {
            return false;
        }
    }

}
