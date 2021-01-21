package dao.offer;

import java.util.Collection;
import javax.ejb.Local;
import model.Article;
import model.Promotion;
import model.User;
import shared.params.PromoParams;

@Local
public interface OfferDAOLocal {

    Collection<Promotion> getDailyPromotions();

    boolean checkIfExists(Collection<Long> promotions);

    double checkPrice(User user, Article article, PromoParams params);
}
