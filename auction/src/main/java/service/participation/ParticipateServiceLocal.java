package service.participation;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import model.Participation;
import shared.dto.UserParticipate;

@Local
public interface ParticipateServiceLocal {

    Participation participate(UserParticipate participation, String login, long id);

    Collection<Article> getMyParticipatedArticles(String login);

    Article getOneParticipatedArticle(String login, long id);
}
