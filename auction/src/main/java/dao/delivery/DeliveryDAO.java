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
        ArticleToDeliver atd = new ArticleToDeliver();
        Delivery d = new Delivery(
                DeliveryStep.WAITING,
                a.getDescription(),
                price,
                atd
        );

        u.addDelivery(d);

        em.merge(d);
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
}
