package service.offer;

import dao.article.ArticleDAOLocal;
import dao.user.UserDAOLocal;
import dao.offer.OfferDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import model.Promotion;
import model.User;
import shared.ErrorMessageManager;
import shared.entities.Price;
import shared.params.PromoParams;
import web.exceptions.BadValuesException;

@Stateless
public class OfferService implements OfferServiceLocal {

    @EJB
    private OfferDAOLocal offer;
    @EJB
    private ParticipationDAOLocal participation;
    @EJB
    private ArticleDAOLocal articleDao;
    @EJB
    private UserDAOLocal userDao;

    @Override
    public Collection<Promotion> getDailyPromotions() {
        return offer.getDailyPromotions();
    }

    @Override
    public Price checkPrice(String login, long id, PromoParams params) {
        User user = userDao.getOne(login);
        Article article = articleDao.getOne(id);
        // vérification si les promotions existent
        if (offer.checkIfExists(params.getPromotions())) {
            // on vérifie si l'article est en vente
            if (article != null && article.getAuction() != null) {
                // on vérifie si l'utilisateur est un participant
                if (participation.isABidder(user, article.getAuction())) {
                    return new Price(offer.checkPrice(user, article, params));
                } else {
                    throw new BadValuesException(ErrorMessageManager.USER_NOT_A_BIDDER);
                }
            } else {
                throw new BadValuesException(ErrorMessageManager.NOT_IN_SELL);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.PROMOTION_NOT_FOUND);
        }
    }
}
