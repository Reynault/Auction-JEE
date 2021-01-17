package dao.participate;

import dao.auth.UserDAOLocal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import model.Auction;
import model.Participation;
import model.User;
import service.date.DateServiceLocal;

@Stateless
public class ParticipationDAO implements ParticipationDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private DateServiceLocal date;

    @EJB
    private UserDAOLocal users;

    public boolean isABidder(String login, long id) {
        Query query = em.createNamedQuery("User.isABidder");
        query.setParameter("id", id);
        query.setParameter("login", login);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean valueIsGreater(double value, long id) {
        Query query = em.createNamedQuery("Auction.findOne", Auction.class);
        query.setParameter("id", id);
        try {
            Auction a = (Auction) query.getSingleResult();
            return a.getBestPrice() < value;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Participation updateParticipation(double value, String login, long id) {
        Query query = em.createNamedQuery("User.findOnePart", User.class);
        query.setParameter("login", login);
        query.setParameter("id", id);

        Participation p;
        try {
            p = (Participation) query.getSingleResult();
            p.setPrice(value);
            p = em.merge(p);
        } catch (NoResultException e) {
            User user = users.getOne(login);
            Article a = em.find(Article.class, id);
            Auction au = a.getAuction();
            p = new Participation(value, user, au);

            user.addParticipation(p);
            em.merge(user);
            au.setBest(p);
            em.merge(au);
        }

        return p;
    }

    @Override
    public List<Article> getMyParticipatedArticles(String login) {
        Query query = em.createNamedQuery("User.getParticipations");
        query.setParameter("login", login);
        query.setParameter("date", Date.from(Instant.now()));
        return (List<Article>) query.getResultList();
    }

    @Override
    public Article getOneParticipatedArticle(String login, long id) {
        Query query = em.createNamedQuery("User.getOneParticipation");
        query.setParameter("login", login);
        query.setParameter("id", id);
        query.setParameter("date", Date.from(Instant.now()));
        try {
            return (Article) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
