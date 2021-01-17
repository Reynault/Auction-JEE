package dao.auction;

import java.time.Instant;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Article;
import model.Auction;
import service.date.DateServiceLocal;

@Stateless
public class AuctionDAO implements AuctionDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private DateServiceLocal date;

    @Override
    public boolean isSold(long article_id) {
        Article a = em.find(Article.class, article_id);
        System.out.println(a);
        return a != null && a.getAuction() != null;
    }

    @Override
    public boolean isFinished(long article_id) {
        Article a = em.find(Article.class, article_id);
        return a.getAuction().getTimeLimit().before(Date.from(Instant.now()));
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
