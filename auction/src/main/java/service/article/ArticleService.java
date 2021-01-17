package service.article;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import service.date.DateServiceLocal;
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

    @EJB
    private DateServiceLocal date;

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
                try {
                    // if date is valid
                    if (date.isPast(auction.getTimeLimit(), Date.from(Instant.now()))) {
                        return dao.sellOne(auction, login, id);
                    } else {
                        throw new BadValuesException("La date doit être dans le futur");
                    }
                } catch (ParseException ex) {
                    throw new BadValuesException("Date non conforme");
                }
            } else {
                throw new BadValuesException("Article déjà en vente");
            }
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }

    @Override
    public void delete(long id, String login) {
        if (dao.ownArticle(id, login)) {
            dao.delete(id, login);
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }

    @Override
    public void remove(long id, String login) {
        if (dao.ownArticle(id, login)) {
            auc.remove(id, login);
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
        if (auc.isSold(id) && !auc.isFinished(id)) {
            return dao.getOne(id);
        } else {
            throw new BadValuesException("Article inexistant");
        }
    }

    @Override
    public Collection<Article> getMine(String login) {
        return dao.getMine(login);
    }

    @Override
    public Article getOneOfMine(long id, String login) {
        if (dao.ownArticle(id, login)) {
            return dao.getOne(id);
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }
}
