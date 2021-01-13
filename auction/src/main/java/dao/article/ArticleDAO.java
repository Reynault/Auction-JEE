package dao.article;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;

/**
 * DAO qui va communiquer avec la base de données avec l'aide de l'ORM JPA pour
 * récupérer des informations à propos des articles.
 */
@Stateless
public class ArticleDAO implements ArticleDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

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
}
