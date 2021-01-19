package service.offer;

import java.util.Collection;
import javax.ejb.Local;
import model.Promotion;
import shared.entities.Price;
import shared.params.PromoParams;

@Local
public interface OfferServiceLocal {

    Collection<Promotion> getDailyPromotions();

    Price checkPrice(String login, long id, PromoParams params);

}
