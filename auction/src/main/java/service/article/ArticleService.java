package service.article;

import dao.article.ArticleDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;
import web.exceptions.BadValuesException;

@Stateless
public class ArticleService implements ArticleServiceLocal {

    @EJB
    private ArticleDAOLocal dao;

    @Override
    public Collection<Article> getAll(SearchParams search) {
        return dao.getAll(search);
    }

    @Override
    public Article getOne(long id) {
        Article a = dao.getOne(id);
        if (a != null) {
            return a;
        } else {
            throw new BadValuesException("Article inexistant");
        }
    }

    @Override
    public Collection<Article> getMine(String login) {
        return dao.getMine(login);
    }

    @Override
    public Article postOne(ArticleCreation article, String login) {
//        return ArticleEntity.convertArticleToEntity(dao.postOne(article, login));
        return dao.postOne(article, login);
    }

    @Override
    public Article sellOne(AuctionCreation auction, String login) {
        return null;
    }

    @Override
    public void delete(long id, String login) {
        if (dao.delete(id, login) == 0) {
            throw new BadValuesException("Article inexistant pour cet utilisateur");
        }
    }

    @Override
    public void removeFromMarket(long id, String login) {
        if (dao.removeFromMarket(id, login) == 0) {
            throw new BadValuesException("Article inexistant pour cet utilisateur");
        }
    }
}
