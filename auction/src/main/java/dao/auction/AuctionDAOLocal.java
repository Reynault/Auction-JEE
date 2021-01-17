package dao.auction;

import javax.ejb.Local;

@Local
public interface AuctionDAOLocal {

    boolean isSold(long article_id);

    boolean isFinished(long article_id);

    int remove(long id, String login);
}
