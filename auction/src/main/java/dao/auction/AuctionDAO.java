package dao.auction;

import java.time.Instant;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import model.Auction;

@Stateless
public class AuctionDAO implements AuctionDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public boolean isSold(long article_id) {
        Query query = em.createNamedQuery("Auction.findOne", Auction.class);
        query.setParameter("id", article_id);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
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

    @Override
    public void remove(long id, String login) {
        Article a = em.find(Article.class, id);
        if (a.getAuction() != null) {
            Auction au = a.getAuction();
            a.setAuction(null);
            em.merge(a);
            em.remove(au);
        }
    }
}
