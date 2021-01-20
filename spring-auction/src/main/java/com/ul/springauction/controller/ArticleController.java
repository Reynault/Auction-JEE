package com.ul.springauction.controller;

import com.ul.springauction.services.article.ArticleService;
import com.ul.springauction.shared.dto.ArticleAdd;
import com.ul.springauction.shared.dto.AuctionAdd;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/auction/articles")
    public List<Article> findAllArticles(@RequestParam(required = false) String n, @RequestParam(required = false) List<String> c){
        return articleService.getAllArticles(n, c);
    }

    @GetMapping(value = "/auction/articles/my")
    public List<Article> findAllOwnArticle(@RequestHeader(name = "Authorization") String token){
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

    @PostMapping(value = "/auction/articles/{id}/sell")
    public Article addAuctionToArticle(@RequestHeader(name = "Authorization") String token, @PathVariable long id, @RequestBody AuctionAdd auction) throws BadRequestException {
        return articleService.addAuctionToArticle(token, id, auction);
    }

    @DeleteMapping(value = "/auction/articles/{id}/delete")
    public void deleteArticle(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        articleService.deleteArticle(token, id);
    }



    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleBadRequest(BadRequestException e){
        return new ErrorResponse(e.getMessage());
    }
}
