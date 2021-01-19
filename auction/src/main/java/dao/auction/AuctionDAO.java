package dao.auction;

import java.time.Instant;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Article;
import model.Auction;

@Stateless
public class AuctionDAO implements AuctionDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public boolean isSold(long article_id) {
        Article a = em.find(Article.class, article_id);
        if (a != null) {
            if (a.getAuction() != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasBeenSold(long article_id) {
        Article a = em.find(Article.class, article_id);
        return a != null && a.isHasBeenSold();
    }

    @Override
    public boolean isFinished(long article_id) {
        Article a = em.find(Article.class, article_id);
        return a.getAuction() != null && a.getAuction().getTimeLimit().before(Date.from(Instant.now()));
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
