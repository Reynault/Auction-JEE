package dao.participate;

import dao.user.UserDAOLocal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
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

    public boolean isBestBidder(String login, Auction auction) {
        return auction.getBest().getBidderLogin().equals(login);
    }

    public boolean isABidder(User user, Auction auction) {
        for (Participation p : auction.getParticipations()) {
            if (p.getBidderLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean valueIsGreater(double value, Auction auction) {
        return auction.getBestPrice() < value;
    }

    @Override
    public Participation updateParticipation(double value, String login, long id) {
        Query query = em.createNamedQuery("Auction.findUserParticipation", Participation.class);
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
            p = new Participation(value, user);
            em.persist(p);

            au.addParticipation(p);
            au.setBest(p);
            em.merge(au);
        }
        return p;
    }

    @Override
    public Collection<Article> getMyParticipatedArticles(String login) {
        Query query = em.createNamedQuery("Auction.getParticipations");
        query.setParameter("login", login);
        query.setParameter("date", Date.from(Instant.now()));
        return (Collection<Article>) query.getResultList();
    }

    @Override
    public Article getOneParticipatedArticle(String login, long id) {
        Query query = em.createNamedQuery("Auction.getOneParticipation");
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
