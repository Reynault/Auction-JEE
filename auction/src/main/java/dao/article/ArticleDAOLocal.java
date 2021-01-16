package dao.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import shared.dto.ArticleCreation;
import shared.params.SearchParams;

@Local
public interface ArticleDAOLocal {

    Collection<Article> getAll(SearchParams search);

    Article getOne(long id);

    Collection<Article> getMine(String login);

    Article postOne(ArticleCreation article, String login);

    int delete(long id, String login);

    int removeFromMarket(long id, String login);
}
