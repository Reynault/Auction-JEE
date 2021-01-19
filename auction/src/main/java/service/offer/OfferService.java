package service.offer;

import dao.auction.AuctionDAOLocal;
import dao.offer.OfferDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Promotion;
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
    private AuctionDAOLocal auction;

    @Override
    public Collection<Promotion> getDailyPromotions() {
        return offer.getDailyPromotions();
    }

    @Override
    public Price checkPrice(String login, long id, PromoParams params) {
        // check if promotions exist
        if (offer.checkIfExists(params.getPromotions())) {
            // check if article is sold
            if (auction.isSold(id)) {// check if user is bidder
                if (participation.isABidder(login, id)) {
                    return new Price(offer.checkPrice(login, id, params));
                } else {
                    throw new BadValuesException("L'utilisateur ne participe pas à cette enchère");
                }
            } else {
                throw new BadValuesException("L'article n'est pas en vente");
            }
        } else {
            throw new BadValuesException("Promotion non trouvée");
        }
    }
}
