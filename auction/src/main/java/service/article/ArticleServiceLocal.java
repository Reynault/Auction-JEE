package service.article;

import java.util.Collection;
import javax.ejb.Local;
import shared.dto.ArticleCreation;
import shared.entities.Entity;
import shared.params.SearchParams;

@Local
public interface ArticleServiceLocal {

    Collection<Entity> getAll(SearchParams search);

    Entity getOne(long id);

    Collection<Entity> getMine(String login);

    Entity postOne(ArticleCreation article, String login);

    void delete(long id, String login);

    void removeFromMarket(long id, String login);
}
