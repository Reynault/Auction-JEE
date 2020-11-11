package singleton;

import entities.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class BDDInit {

    @PersistenceContext(name = "AuctionPU")
    private EntityManager em;

    public BDDInit() {
        System.out.println("---------SINGLETON INITITIALIZED----------");
    }

    @PostConstruct
    public void init() {
        System.out.println("----------INSERTING DATA----------");
        User bob = new User("Bob", "123");
        User alice = new User("Alice", "root");

        em.persist(bob);
        em.persist(alice);
        System.out.println("----------END OF INSERTION----------");
    }
}
