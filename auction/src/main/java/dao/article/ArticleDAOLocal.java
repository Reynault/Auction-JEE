package dao.article;

import model.Article;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ArticleDAOLocal {

    Collection<Article> getAll();

    public Article getOne(long id);
}
