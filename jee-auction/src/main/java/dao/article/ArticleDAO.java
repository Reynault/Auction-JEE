package dao.article;

import model.Article;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DAO that'll communicate with the database in order to
 * fetch and modify information linked with an article
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
