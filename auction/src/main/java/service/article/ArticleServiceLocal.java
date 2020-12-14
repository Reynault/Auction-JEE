package service.article;

import java.util.Collection;
import javax.ejb.Local;
import shared.entities.ArticleEntity;

@Local
public interface ArticleServiceLocal {

    Collection<ArticleEntity> getAll();
}
