package service.article;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
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

    @EJB
    private AuctionDAOLocal auc;

    @Override
    public Article postOne(ArticleCreation article, String login) {
        return dao.postOne(article, login);
    }

    @Override
    public Article sellOne(AuctionCreation auction, String login, long id) {
        // if user has article
        if (dao.ownArticle(id, login)) {
            // if article isn't in an auction
            if (!auc.isSold(id)) {
                return dao.sellOne(auction, login, id);
            } else {
                throw new BadValuesException("Article déjà en vente");
            }
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }

    @Override
    public void delete(long id, String login) {
        if (dao.delete(id, login) == 0) {
            throw new BadValuesException("Article inexistant pour cet utilisateur");
        }
    }

    @Override
    public void remove(long id, String login) {
        if (dao.ownArticle(id, login)) {
            if (auc.remove(id, login) == 0) {
                throw new BadValuesException("L'article n'était pas en vente");
            }
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }

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
}
