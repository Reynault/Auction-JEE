package com.ul.springauction.controller;

import com.ul.springauction.model.Article;
import com.ul.springauction.services.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/articles")
    public Iterable<Article> findAllArticle(){
        return articleService.findAll();
    }
}
