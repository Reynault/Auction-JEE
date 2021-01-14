package service.article;

import java.util.Collection;
import javax.ejb.Local;
import shared.dto.ArticleCreation;
import shared.entities.ArticleEntity;
import shared.entities.Entity;

@Local
public interface ArticleServiceLocal {

    Collection<ArticleEntity> getAll();

    Entity getOne(long id);

    public Collection<ArticleEntity> getMine(String login);

    public ArticleEntity postOne(ArticleCreation article, String login);
}
