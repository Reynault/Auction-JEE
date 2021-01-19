package dao.offer;

import java.util.Collection;
import javax.ejb.Local;
import model.Promotion;
import shared.params.PromoParams;

@Local
public interface OfferDAOLocal {

    Collection<Promotion> getDailyPromotions();

    boolean checkIfExists(Collection<Long> promotions);

    double checkPrice(String login, long id, PromoParams params);
}
