package dao.participate;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import model.Participation;

@Local
public interface ParticipationDAOLocal {

    boolean valueIsGreater(double value, long id);

    Participation updateParticipation(double value, String login, long id);

    Collection<Article> getMyParticipatedArticles(String login);

    Article getOneParticipatedArticle(String login, long id);

    boolean isABidder(String login, long id);
}
