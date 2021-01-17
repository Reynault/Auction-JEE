package dao.article;

import dao.auth.UserDAOLocal;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Article;
import model.Auction;
import model.Category;
import model.User;
import service.date.DateServiceLocal;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import shared.params.SearchParams;

/**
 * DAO qui va communiquer avec la base de donnees avec l'aide de l'ORM JPA pour
 * recuperer des informations a propos des articles.
 */
@Stateless
public class ArticleDAO implements ArticleDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private UserDAOLocal users;

    @EJB
    private DateServiceLocal date;

    private List<Category> mergeStringList(List<String> s) {
        ArrayList<Category> cat = new ArrayList();
        for (String c : s) {
            Query query = em.createNamedQuery("Category.findOne", Integer.class);
            query.setParameter("name", c);
            try {
                cat.add((Category) query.getSingleResult());
            } catch (NoResultException e) {
                cat.add(new Category(c));
            }
        }
        return cat;
    }

    @Override
    public boolean ownArticle(long id, String login) {
        Query query = em.createNamedQuery("User.own", User.class);
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
    public Article postOne(ArticleCreation article, String login) {
        User u = users.getOne(login);
        Article a = new Article(
                article.getName(),
                article.getDescription(),
                mergeStringList(article.getCategories()),
                null
        );

        em.persist(a);
        u.addArticle(a);
        em.merge(u);
        return a;
    }

    @Override
    public Article sellOne(AuctionCreation auction, String login, long id) {
        Article a = this.getOne(id);

        System.out.println("TEST");
        try {
            Auction au = new Auction(
                    auction.getFirstPrice(),
                    date.transformIntoDate(auction.getTimeLimit()),
                    null,
                    new ArrayList(),
                    a);
            System.out.println("TEST");
            em.persist(au);
            a.setAuction(au);
        } catch (ParseException ex) {
            return null;
        }

        return em.merge(a);
    }

    @Override
    public Collection<Article> getAll(SearchParams search) {

        List<String> c = search.getCategories();

        String joins = "";
        String params = "";

        for (int i = 0; i < c.size(); i++) {
            joins += " JOIN a.categories c" + i;
            params += " AND c" + i + ".name LIKE :c" + i;
        }

        String stringQuery = "SELECT a FROM Article a " + joins + " WHERE "
                + "a.name LIKE :n" + params + " AND a.auction <> NULL AND a.auction.timeLimit > :d";

        TypedQuery<Article> a = (TypedQuery<Article>) em.createQuery(stringQuery);

        a.setParameter("n", "%" + search.getName() + "%");
        a.setParameter("d", Date.from(Instant.now()));
        for (int i = 0; i < c.size(); i++) {
            a.setParameter("c" + i, "%" + c.get(i) + "%");
        }

        return a.getResultList();
    }

    @Override
    public Article getOne(long id) {
        Query query = em.createNamedQuery("Article.findOne", Article.class);
        query.setParameter("id", id);
        try {
            return (Article) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Article> getMine(String login) {
        Query query = em.createNamedQuery("User.findMine", Article.class);
        query.setParameter("login", login);
        return query.getResultList();
    }

    @Override
    public void delete(long id, String login) {
        Article a = em.find(Article.class, id);
        User u = users.getOne(login);
        u.removeArticle(id);
        em.merge(u);
        em.remove(a);
    }

}
