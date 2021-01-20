package com.ul.springauction.DAO;

import model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findById(long id);
    List<Article> findByNameContaining(String name);
    void deleteById(long id);
}
