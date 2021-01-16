package dao.article;

import dao.auth.UserDAOLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import model.Auction;
import model.Category;
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
        Query query = em.createNamedQuery("Article.own", Article.class);
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
    public boolean exists(long id) {
        Query query = em.createNamedQuery("Article.findOne", Integer.class);
        query.setParameter("id", id);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Article postOne(ArticleCreation article, String login) {
        Article a = new Article(
                article.getName(),
                article.getDescription(),
                users.getOne(login),
                mergeStringList(article.getCategories()),
                null
        );
        return em.merge(a);
    }

    @Override
    public Article sellOne(AuctionCreation auction, String login, long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Article a = this.getOne(id);

        try {
            a.setAuction(new Auction(
                    auction.getFirstPrice(),
                    sdf.parse(auction.getTimeLimit()),
                    new ArrayList(),
                    null,
                    null,
                    a
            ));
        } catch (ParseException ex) {
            return null;
        }

        em.merge(a);
        return a;
    }

    @Override
    public Collection<Article> getAll(SearchParams search) {
//        String stringQuery = "SELECT a FROM Article a WHERE "
//                + "a.name LIKE :n' AND ";
//
//        Iterator<String> i = search.getCategories().iterator();
//
//        if (i.hasNext()) {
//            stringQuery += "(0 < LOCATE(:c, a.name))";
//            i.next();
//        }
//
//        while (i.hasNext()) {
//
//        }
//
//        TypedQuery<Article> a = (TypedQuery<Article>) em.createQuery(stringQuery);
//        Query query = em.createNamedQuery("Article.findAll", Article.class);
//        return query.getResultList();
        return null;
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
        Query query = em.createNamedQuery("Article.findMine", Article.class);
        query.setParameter("login", login);
        return query.getResultList();
    }

    @Override
    public int delete(long id, String login) {
        Query query = em.createNamedQuery("Article.delete", Article.class);
        query.setParameter("id", id);
        query.setParameter("login", login);
        return query.executeUpdate();
    }

    @Override
    public int removeFromMarket(long id, String login) {
//        Query query = em.createNamedQuery("Article.changeDate", Article.class);
//        query.setParameter("id", id);
//        query.setParameter("login", login);
//        query.setParameter("date", Date.from(Instant.now()));
//        return query.executeUpdate();
        return 0;
    }

}
