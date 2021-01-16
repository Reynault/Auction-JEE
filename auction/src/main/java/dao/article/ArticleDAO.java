package dao.article;

import dao.auth.UserDAOLocal;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Article;
import shared.dto.ArticleCreation;
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

    @Override
    public Collection<Article> getAll(SearchParams search) {
        String stringQuery = "SELECT a FROM Article a WHERE a.name LIKE %:name% AND ";

        Iterator<String> i = search.getCategories().iterator();

        while (i.hasNext()) {

        }

        TypedQuery<Article> a = (TypedQuery<Article>) em.createQuery(stringQuery);
        Query query = em.createNamedQuery("Article.findAll", Article.class);
        return query.getResultList();
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
    public Article postOne(ArticleCreation article, String login) {
        Article a = new Article();
        return em.merge(a);
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
