package dao.article;

import dao.auth.UserDAOLocal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;
import shared.dto.ArticleCreation;

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
    public Collection<Article> getAll() {
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
        Article a = new Article(
                article.getName(),
                article.getDescription(),
                article.getFirstPrice(),
                Date.valueOf(article.getTimeLimit()),
                users.getOne(login),
                new ArrayList(),
                Article.getStringAsCategories(article.getCategories()),
                new ArrayList(),
                null
        );
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
        Query query = em.createNamedQuery("Article.removeFromMarket", Article.class);
        query.setParameter("id", id);
        query.setParameter("login", login);
        query.setParameter("data", Date.from(Instant.now()));
        return query.executeUpdate();
    }
}
