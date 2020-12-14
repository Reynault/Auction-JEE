package dao.article;

import model.Article;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

/**
 * DAO qui va communiquer avec la base de données avec l'aide
 * de l'ORM JPA pour récupérer des informations à propos des articles.
 */
@Stateless
public class ArticleDAO implements ArticleDAOLocal{
    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public Collection<Article> getAll() {
        Query query = em.createNamedQuery("Article.findAll", Article.class);
        return query.getResultList();
    }
}
