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
    public boolean isFinished(Article a) {
        return a != null
                && a.getAuction() != null
                && a.getAuction().getTimeLimit().before(Date.from(Instant.now()));
    }

    @Override
    public void remove(Article a) {
        if (a.getAuction() != null) {
            Auction au = a.getAuction();
            a.setAuction(null);
            em.merge(a);
            em.remove(au);
        }
    }
}
