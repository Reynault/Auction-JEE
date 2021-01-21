package dao.article;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import model.User;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;

@Local
public interface ArticleDAOLocal {

    Collection<Article> getAll(SearchParams search);

    boolean ownArticle(Article a, User u);

    Article getOne(long id);

    Collection<Article> getMine(String login);

    Article postOne(ArticleCreation article, String login);

    Article sellOne(AuctionCreation auction, String login, long id);

    void delete(long id, String login);
}
