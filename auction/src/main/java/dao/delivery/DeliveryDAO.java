package dao.delivery;

import dao.auth.UserDAOLocal;
import enumeration.DeliveryStep;
import java.util.Collection;
import javax.ejb.EJB;
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
import shared.dto.UserAddress;

@Stateless
public class DeliveryDAO implements DeliveryDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private UserDAOLocal user;

    @Override
    public Delivery initializeDelivery(UserAddress address, double price, String login, long id) {
        User u = user.getOne(login);

        Article a = em.find(Article.class, id);
        ArticleToDeliver atd = new ArticleToDeliver(
                a.getName(),
                a.getDescription()
        );
        Delivery d = new Delivery(
                DeliveryStep.IN_PROCESS,
                price,
                new Address(
                        address.getCountry(),
                        address.getCity(),
                        address.getStreet(),
                        address.getCode()
                ),
                atd
        );

        em.persist(d);
        u.addDelivery(d);
        em.merge(u);
        a.setHasBeenSold(true);
        em.merge(a);
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
}
