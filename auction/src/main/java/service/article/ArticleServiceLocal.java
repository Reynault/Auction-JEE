package service.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.entities.Entity;
import shared.params.SearchParams;

@Local
public interface ArticleServiceLocal {

    Article postOne(ArticleCreation article, String login);

    Entity sellOne(AuctionCreation auction, String login);

    Collection<Entity> getAll(SearchParams search);

    Entity getOne(long id);

    Collection<Entity> getMine(String login);

    void delete(long id, String login);

    void removeFromMarket(long id, String login);
}
