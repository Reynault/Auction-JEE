package dao.article;

import model.Article;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;

/**
 * DAO that'll communicate with the database in order to
 * fetch and modify information linked with an article
 */
@Stateless
public class ArticleDAO implements ArticleDAOLocal{
    @Override
    public Collection<Article> getAll() {
        System.out.println("HELLO");
        return new ArrayList<>();
    }
}
