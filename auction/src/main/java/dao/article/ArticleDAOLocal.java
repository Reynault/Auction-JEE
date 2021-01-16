package dao.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;

@Local
public interface ArticleDAOLocal {

    Collection<Article> getAll(SearchParams search);

    boolean ownArticle(long id, String login);

    boolean exists(long id);

    Article getOne(long id);

    Collection<Article> getMine(String login);

    Article postOne(ArticleCreation article, String login);

    Article sellOne(AuctionCreation auction, String login, long id);

    int delete(long id, String login);

    int removeFromMarket(long id, String login);
}
