package com.ul.springauction.controller;

import com.ul.springauction.services.article.ArticleService;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/auction/articles/my")
    public List<Article> findAllArticle(@RequestHeader(name = "Authorization") String token){
        return articleService.findUserArticles(token);
    }
}
