package service.offer.rotation;

import dao.offer.types.Offer;
import java.util.HashMap;
import java.util.List;

public class Random implements RotationStrategy {

    private int nbOffers;

    public Random(int nbOffers) {
        this.nbOffers = nbOffers;
    }

    @Override
    public HashMap<Integer, Offer> rotate(List<Offer> offers) {
        HashMap<Integer, Offer> current = new HashMap();
        for (int i = 0; i < nbOffers; i++) {
            int random = (int) (Math.random() * offers.size());
            current.put(i, offers.get(random));
            offers.remove(random);
        }
        return current;
    }

}
