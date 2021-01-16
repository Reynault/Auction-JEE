package service.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;

@Local
public interface ArticleServiceLocal {

    Article postOne(ArticleCreation article, String login);

    Article sellOne(AuctionCreation auction, String login);

    Collection<Article> getAll(SearchParams search);

    Article getOne(long id);

    Collection<Article> getMine(String login);

    void delete(long id, String login);

    void removeFromMarket(long id, String login);
}
