package service;

import model.Article;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ArticleServiceLocal {
    Collection<Article> getAll();
}
