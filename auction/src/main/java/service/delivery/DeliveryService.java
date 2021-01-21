package service.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.auction.AuctionDAOLocal;
import dao.auth.UserDAOLocal;
import dao.delivery.DeliveryDAOLocal;
import dao.offer.OfferDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Delivery;
import service.messaging.sender.MessagingServiceLocal;
import service.messaging.RessourceManager;
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
    private MessagingServiceLocal messaging;

    @Override
    public Delivery deliver(UserAddress address, PromoParams params, String login, long id) {
        // the auction is finished and to be sold
        if (auction.isSold(id) && !auction.hasBeenSold(id)) {
            if (auction.isFinished(id)) {
                // and the user is the best bidder
                if (participation.isBestBidder(login, id)) {
                    address = user.getAddress(login, address);
                    if (address != null) {
                        try {
                            double price = offer.checkPrice(login, id, params);
                            Delivery delivery = dao.initializeDelivery(address, price, login, id);
                            ObjectMapper mapper = new ObjectMapper();
                            messaging.sendMessage(
                                    mapper.writeValueAsBytes(delivery),
                                    RessourceManager.PENDING_DELIVERIES
                            );
                            return delivery;
                        } catch (IOException | TimeoutException ex) {
                            Logger.getLogger(DeliveryService.class.getName()).log(Level.SEVERE, null, ex);
                            return null;
                        }
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
        if (dao.hasDelivery(login, id)) {
            return dao.getOneDelivery(login, id);
        } else {
            throw new BadValuesException("Commande non trouvée");
        }
    }
}
