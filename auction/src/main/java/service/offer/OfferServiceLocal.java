package service.offer;

import java.util.Collection;
import javax.ejb.Local;
import model.Promotion;
import shared.params.PromoParams;

@Local
public interface OfferServiceLocal {

    Collection<Promotion> getDailyPromotions();

    double checkPrice(String login, long id, PromoParams params);

}
