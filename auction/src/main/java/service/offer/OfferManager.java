package service.offer;

import dao.offer.types.Offer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import service.offer.rotation.Random;
import service.offer.rotation.RotationStrategy;

@Singleton
@Startup
public class OfferManager {

    private RotationStrategy strategy;

    private int numberOfCurrentOffers;

    private List<Offer> offers;

    private HashMap<Integer, Offer> currentOffers;

    @PostConstruct
    public void init() {
        offers = Arrays.asList();

        numberOfCurrentOffers = 2;
        strategy = new Random(numberOfCurrentOffers);

        currentOffers = this.strategy.rotate(offers);
    }

    public HashMap<Integer, Offer> getCurrentOffers() {
        return this.currentOffers;
    }

    @Schedules({
        @Schedule(second = "0", minute = "0", hour = "0",
                dayOfMonth = "*", month = "*", year = "*")})
    public void changeDailyOffers() {
        currentOffers = this.strategy.rotate(offers);
    }
}
