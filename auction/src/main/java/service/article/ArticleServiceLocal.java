package service.article;

import java.util.Collection;
import javax.ejb.Local;
import shared.entities.ArticleEntity;
import shared.entities.Entity;

@Local
public interface ArticleServiceLocal {

    Collection<ArticleEntity> getAll();

    Entity getOne(long id);
}
