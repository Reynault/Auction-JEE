package com.ul.springauction.DAO;

//import com.ul.springauction.model.Article;
import model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Iterable<Article> findAll();
}
