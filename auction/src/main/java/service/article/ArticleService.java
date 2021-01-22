package service.article;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import dao.user.UserDAOLocal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import model.User;
import service.date.DateServiceLocal;
import shared.ErrorMessageManager;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;
import web.exceptions.BadValuesException;

@Stateless
public class ArticleService implements ArticleServiceLocal {

    @EJB
    private ArticleDAOLocal articleDao;

    @EJB
    private AuctionDAOLocal auctionDao;

    @EJB
    private UserDAOLocal userDao;

    @EJB
    private DateServiceLocal date;

    @Override
    public Article postOne(ArticleCreation article, String login) {
        return articleDao.postOne(article, login);
    }

    @Override
    public Article sellOne(AuctionCreation auction, String login, long id) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        // si l'utilisateur possède l'article
        if (user != null && article != null && articleDao.ownArticle(article, user)) {
            // si l'article n'a pas déjà été vendu
            if (!article.isHasBeenSold()) {
                // si l'article n'est pas déjà en vente
                if (article.getAuction() == null) {
                    try {
                        // si la date fourni est cohérente
                        if (date.isPast(auction.getTimeLimit(), Date.from(Instant.now()))) {
                            return articleDao.sellOne(auction, login, id);
                        } else {
                            throw new BadValuesException(ErrorMessageManager.DATE_NOT_IN_THE_FUTUR);
                        }
                    } catch (ParseException ex) {
                        throw new BadValuesException(ErrorMessageManager.BAD_DATE_FORMAT);
                    }
                } else {
                    throw new BadValuesException(ErrorMessageManager.ALREADY_IN_SELL);
                }
            } else {
                throw new BadValuesException(ErrorMessageManager.ALREADY_SOLD);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }

    @Override
    public void delete(long id, String login) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        if (user != null && article != null && articleDao.ownArticle(article, user)) {
            articleDao.delete(id, login);
        } else {
            throw new BadValuesException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }

    @Override
    public void remove(long id, String login) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        if (user != null && article != null && articleDao.ownArticle(article, user)) {
            if (article.getAuction() != null) {
                auctionDao.remove(article);
            } else {
                throw new BadValuesException(ErrorMessageManager.NOT_IN_SELL);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }

    @Override
    public Collection<Article> getAll(SearchParams search) {
        return articleDao.getAll(search);
    }

    @Override
    public Article getOne(long id) {
        Article article = articleDao.getOne(id);
        if (article != null
                && article.getAuction() != null
                && !auctionDao.isFinished(article)) {
            return articleDao.getOne(id);
        } else {
            throw new BadValuesException(ErrorMessageManager.ARTICLE_NOT_FOUND);
        }
    }

    @Override
    public Collection<Article> getMine(String login) {
        return articleDao.getMine(login);
    }

    @Override
    public Article getOneOfMine(long id, String login) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        if (user != null && article != null && articleDao.ownArticle(article, user)) {
            return articleDao.getOne(id);
        } else {
            throw new BadValuesException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }
}
