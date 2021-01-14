package dao.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import shared.dto.ArticleCreation;

@Local
public interface ArticleDAOLocal {

    Collection<Article> getAll();

    Article getOne(long id);

    Collection<Article> getMine(String login);

    Article postOne(ArticleCreation article, String login);
}
