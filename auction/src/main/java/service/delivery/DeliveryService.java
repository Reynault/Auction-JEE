package service.delivery;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import dao.delivery.DeliveryDAOLocal;
import dao.offer.OfferDAOLocal;
import dao.participate.ParticipationDAOLocal;
import dao.user.UserDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Address;
import model.Article;
import model.Delivery;
import model.User;
import service.messaging.sender.DeliverySenderLocal;
import shared.ErrorMessageManager;
import shared.dto.UserAddress;
import shared.params.PromoParams;
import web.exceptions.BadValuesException;

@Stateless
public class DeliveryService implements DeliveryServiceLocal {

    @EJB
    private DeliverySenderLocal deliverySender;
    @EJB
    private DeliveryDAOLocal dao;
    @EJB
    private ParticipationDAOLocal participation;
    @EJB
    private ArticleDAOLocal articleDao;
    @EJB
    private AuctionDAOLocal auctionDao;
    @EJB
    private UserDAOLocal userDao;
    @EJB
    private OfferDAOLocal offer;

    @Override
    public Delivery deliver(UserAddress address, PromoParams params, String login, long id) {
        Article article = articleDao.getOne(id);
        User user = userDao.getOne(login);
        if (article != null && user != null) {
            // est-ce que les promotions existent et sont journalières ?
            if (offer.checkIfExists(params.getPromotions())) {
                // article en vente
                if (article.getAuction() != null && !article.isHasBeenSold()) {
                    // vente non finie
                    if (auctionDao.isFinished(article)) {
                        // l'utilisateur est le meilleur participant
                        if (participation.isBestBidder(login, article.getAuction())) {
                            // récupération de l'addresse
                            Address a;
                            if (address != null) {
                                a = new Address(address.getCountry(), address.getCity(), address.getStreet(), address.getCode());
                            } else if (user.getHome() != null) {
                                a = user.getHome();
                            } else {
                                throw new BadValuesException(ErrorMessageManager.USER_MUST_HAVE_ADDRESS);
                            }
                            // modification de l'addresse de l'utilisateur avec la nouvelle
                            user = userDao.changeAddress(user, a);
                            // récupération du nouveaux prix en prenant en compte les promotions
                            double price = offer.checkPrice(user, article, params);
                            // envoie de la demande de commande
                            Delivery delivery = dao.initializeDelivery(a, price, user, article);
                            deliverySender.send(delivery);
                            return delivery;
                        } else {
                            throw new BadValuesException(ErrorMessageManager.USER_NOT_THE_BEST);
                        }
                    } else {
                        throw new BadValuesException(ErrorMessageManager.AUCTION_ALWAYS_RUNNING);
                    }
                } else {
                    throw new BadValuesException(ErrorMessageManager.NOT_IN_SELL);
                }
            } else {
                throw new BadValuesException(ErrorMessageManager.PROMOTION_NOT_FOUND);
            }
        } else {
            throw new BadValuesException(ErrorMessageManager.MISSING_DATA);
        }
    }

    @Override
    public Collection<Delivery> getDeliveries(String login) {
        return dao.getDeliveries(login);
    }

    @Override
    public Delivery getOneDelivery(String login, long id) {
        Delivery d = dao.getOneDelivery(login, id);
        if (d == null) {
            throw new BadValuesException(ErrorMessageManager.COMMAND_NOT_FOUND);

        } else {
            return d;
        }
    }
}
