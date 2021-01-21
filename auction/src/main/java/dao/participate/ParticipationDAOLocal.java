package dao.participate;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import model.Auction;
import model.Participation;
import model.User;

@Local
public interface ParticipationDAOLocal {

    boolean isBestBidder(String login, Auction auction);

    boolean isABidder(User user, Auction auction);

    boolean valueIsGreater(double value, Auction auction);

    Participation updateParticipation(double value, String login, long id);

    Collection<Article> getMyParticipatedArticles(String login);

    Article getOneParticipatedArticle(String login, long id);

}
