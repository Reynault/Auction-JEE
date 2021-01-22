package dao.delivery;

import enumeration.DeliveryStep;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Address;
import model.Article;
import model.ArticleToDeliver;
import model.Delivery;
import model.User;

@Stateless
public class DeliveryDAO implements DeliveryDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public Delivery initializeDelivery(Address address, double price, User user, Article article) {
        ArticleToDeliver atd = new ArticleToDeliver(
                article.getName(),
                article.getDescription()
        );
        Delivery d = new Delivery(
                DeliveryStep.IN_PROCESS,
                price,
                address,
                atd
        );

        em.persist(d);
        user.addDelivery(d);
        em.merge(user);
        article.setHasBeenSold(true);
        em.merge(article);
        return d;
    }

    @Override
    public Collection<Delivery> getDeliveries(String login) {
        Query q = em.createNamedQuery("User.getAllDeliveries");
        q.setParameter("login", login);
        return q.getResultList();
    }

    @Override
    public Delivery getOneDelivery(String login, long id) {
        Query q = em.createNamedQuery("User.getOneDelivery");
        q.setParameter("login", login);
        q.setParameter("id", id);
        try {
            return (Delivery) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean hasDelivery(String login, long id) {
        Query q = em.createNamedQuery("User.hasDelivery");
        q.setParameter("login", login);
        q.setParameter("id", id);
        return (Long) q.getSingleResult() > 0;
    }

    @Override
    public void validateDelivery(Delivery d) {
        d.setState(DeliveryStep.VALID);
        em.merge(d);
    }
}
