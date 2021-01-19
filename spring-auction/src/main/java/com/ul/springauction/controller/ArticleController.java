package com.ul.springauction.controller;

import com.ul.springauction.services.article.ArticleService;
import com.ul.springauction.shared.dto.ArticleAdd;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/auction/articles/my")
    public List<Article> findAllArticle(@RequestHeader(name = "Authorization") String token){
        return articleService.findUserArticles(token);
    }

    @GetMapping(value = "/auction/articles/{id}")
    public Article findArticleWithAuction(@PathVariable long id) throws BadRequestException {
        return articleService.findArticleWithAuction(id);
    }

    @GetMapping(value = "/auction/articles/{id}/my")
    public Article findOwnArticleWithAuction(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        return articleService.findOwnArticleWithAuction(token, id);
    }

    @PostMapping(value = "/auction/articles/submit")
    public Article addArticle(@RequestHeader(name = "Authorization") String token, @RequestBody ArticleAdd article) throws BadRequestException {
        return articleService.addArticle(token, article);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleBadRequest(BadRequestException e){
        return new ErrorResponse(e.getMessage());
    }
}
