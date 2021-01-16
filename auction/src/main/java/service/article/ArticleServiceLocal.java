package service.article;

import java.util.Collection;
import javax.ejb.Local;
import shared.dto.ArticleCreation;
import shared.entities.Entity;

@Local
public interface ArticleServiceLocal {

    Collection<Entity> getAll();

    Entity getOne(long id);

    Collection<Entity> getMine(String login);

    Entity postOne(ArticleCreation article, String login);

    void delete(long id, String login);

    void removeFromMarket(long id, String login);
}
