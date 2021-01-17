package service.offer.rotation;

import dao.offer.types.Offer;
import java.util.HashMap;
import java.util.List;

public interface RotationStrategy {

    HashMap<Integer, Offer> rotate(List<Offer> offers);

}
