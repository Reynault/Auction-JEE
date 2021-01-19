package dao.init.offer.rotation;

import java.util.ArrayList;
import java.util.List;
import model.Promotion;

public class Random implements RotationStrategy {

    private final int nbOffers;

    public Random(int nbOffers) {
        this.nbOffers = nbOffers;
    }

    @Override
    public List<Promotion> rotate(List<Promotion> promotions) {
        List<Promotion> current = new ArrayList();
        if (promotions.size() > 0) {
            for (int i = 0; i < nbOffers; i++) {
                int random = (int) (Math.random() * promotions.size());
                current.add(promotions.get(random));
                promotions.remove(random);
            }
        }
        return current;
    }

}
