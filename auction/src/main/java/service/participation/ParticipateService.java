package service.participation;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import dao.auth.UserDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import model.Participation;
import model.User;
import shared.ErrorMessageManager;
import shared.dto.UserParticipate;
import web.exceptions.BadValuesException;

@Stateless
public class ParticipateService implements ParticipateServiceLocal {

    @EJB
    private ParticipationDAOLocal dao;
    @EJB
    private UserDAOLocal userDao;
    @EJB
    private ArticleDAOLocal articleDao;
    @EJB
    private AuctionDAOLocal auctionDao;

    @Override
    public Participation participate(UserParticipate participation, String login, long id) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        if (article != null && user != null) {
            // if article is not expired
            if (article.getAuction() != null && !auctionDao.isFinished(article)) {
                // if user isn't owner
                if (!articleDao.ownArticle(article, user)) {
                    // if value is greater
                    if (dao.valueIsGreater(participation.getValue(), article.getAuction())) {
                        return dao.updateParticipation(participation.getValue(), login, id);
                    } else {
                        throw new BadValuesException(ErrorMessageManager.BIGGER_VALUE);
                    }
                } else {
                    throw new BadValuesException(ErrorMessageManager.USER_OWN);
                }
            } else {
                throw new BadValuesException(ErrorMessageManager.NOT_IN_SELL);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.MISSING_DATA);
        }
    }

    @Override
    public Collection<Article> getMyParticipatedArticles(String login) {
        return dao.getMyParticipatedArticles(login);
    }

    @Override
    public Article getOneParticipatedArticle(String login, long id) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        if (user != null && article != null) {
            if (dao.isABidder(user, article.getAuction())) {
                Article a = dao.getOneParticipatedArticle(login, id);
                if (a == null) {
                    throw new BadValuesException(ErrorMessageManager.NOT_IN_SELL);
                } else {
                    return a;
                }
            } else {
                throw new BadValuesException(ErrorMessageManager.USER_NOT_A_BIDDER);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.MISSING_DATA);
        }
    }

}
