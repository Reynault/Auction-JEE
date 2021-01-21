package dao.auction;

import javax.ejb.Local;
import model.Article;

@Local
public interface AuctionDAOLocal {

    boolean isFinished(Article a);

    void remove(Article a);

}
