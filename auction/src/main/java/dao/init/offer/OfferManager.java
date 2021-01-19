package dao.init.offer;

import dao.init.offer.rotation.Random;
import dao.init.offer.rotation.RotationStrategy;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Promotion;

@Singleton
@Startup
@DependsOn("DatabaseInit")
public class OfferManager {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;
    private RotationStrategy strategy;
    private int numberOfCurrentOffers;

    @PostConstruct
    public void init() {
        numberOfCurrentOffers = 2;
        strategy = new Random(numberOfCurrentOffers);
        changeDailyOffers();
    }

    @Schedules({
        @Schedule(second = "0", minute = "0", hour = "0",
                dayOfMonth = "*", month = "*", year = "*")})
    public void changeDailyOffers() {
        System.out.println("----CHANGE DAILY OFFERS----");

        Query q = em.createNamedQuery("Promotion.removeDaily");
        q.executeUpdate();

        q = em.createNamedQuery("Promotion.getAll");
        List<Promotion> newDailyOffers = q.getResultList();

        newDailyOffers = strategy.rotate(newDailyOffers);

        for (Promotion o : newDailyOffers) {
            System.out.println("NEW OFFER : " + o.getId());
            o.setDaily(true);
            em.merge(o);
        }
        System.out.println("----DAILY OFFERS CHANGED----");
    }
}
