package service;

import dao.article.ArticleDAOLocal;
import model.Article;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collection;

@Stateless
public class ArticleService implements ArticleServiceLocal {
    @EJB
    private ArticleDAOLocal dao;

    @Override
    public Collection<Article> getAll() {
        return dao.getAll();
    }
}
