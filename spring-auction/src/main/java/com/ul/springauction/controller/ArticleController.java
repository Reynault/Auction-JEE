package com.ul.springauction.controller;

import com.ul.springauction.services.article.ArticleService;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/articles")
    public Iterable<Article> findAllArticle(){
        return articleService.findAll();
    }
}
