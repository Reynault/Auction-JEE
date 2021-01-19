package dao.auction;

import javax.ejb.Local;

@Local
public interface AuctionDAOLocal {

    boolean isSold(long article_id);

    boolean isFinished(long article_id);

    void remove(long id, String login);

    boolean hasBeenSold(long article_id);
}
