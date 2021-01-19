package service.delivery;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import dao.auth.UserDAOLocal;
import dao.delivery.DeliveryDAOLocal;
import dao.offer.OfferDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Delivery;
import shared.dto.UserAddress;
import shared.params.PromoParams;
import web.exceptions.BadValuesException;

@Stateless
public class DeliveryService implements DeliveryServiceLocal {

    @EJB
    private DeliveryDAOLocal dao;
    @EJB
    private ParticipationDAOLocal participation;
    @EJB
    private AuctionDAOLocal auction;
    @EJB
    private UserDAOLocal user;
    @EJB
    private OfferDAOLocal offer;
    @EJB
    private ArticleDAOLocal article;

    @Override
    public Delivery deliver(UserAddress address, PromoParams params, String login, long id) {
        // the auction is finished and to be sold
        if (auction.isSold(id) && !auction.hasBeenSold(id)) {
            if (auction.isFinished(id)) {
                // and the user is the best bidder
                if (participation.isBestBidder(login, id)) {
                    address = user.getAddress(login, address);
                    if (address != null) {
                        double price = offer.checkPrice(login, id, params);
                        return dao.initializeDelivery(address, price, login, id);
                    } else {
                        throw new BadValuesException("L'utilisateur doit donner une addresse");
                    }
                } else {
                    throw new BadValuesException("L'utilisateur doit être le meilleur participant");
                }
            } else {
                throw new BadValuesException("L'enchère n'est pas finie");
            }
        } else {
            throw new BadValuesException("L'article n'est pas en vente");
        }
    }

    @Override
    public Collection<Delivery> getDeliveries(String login) {
        return dao.getDeliveries(login);
    }

    @Override
    public Delivery getOneDelivery(String login, long id) {
        if (article.ownArticle(id, login)) {
            return dao.getOneDelivery(login, id);
        } else {
            throw new BadValuesException("L'utilisateur ne possède pas l'article");
        }
    }
}
