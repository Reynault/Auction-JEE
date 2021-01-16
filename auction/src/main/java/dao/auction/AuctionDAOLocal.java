package dao.auction;

import javax.ejb.Local;
import model.Auction;

@Local
public interface AuctionDAOLocal {

    Auction isSold(long article_id);

    boolean isFinished(long article_id);
}
