package com.ul.springauction.controller;

import com.ul.springauction.services.ArticleService;
import com.ul.springauction.shared.dto.ArticleAdd;
import com.ul.springauction.shared.dto.AuctionAdd;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/auction/articles")
@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "")
    public List<Article> findAllArticles(@RequestParam(required = false) String n, @RequestParam(required = false) List<String> c){
        return articleService.getAllArticles(n, c);
    }

    @GetMapping(value = "/my")
    public List<Article> findAllOwnArticle(@RequestHeader(name = "Authorization") String token){
        return articleService.findUserArticles(token);
    }

    @GetMapping(value = "/{id}")
    public Article findArticleWithAuction(@PathVariable long id) throws BadRequestException {
        return articleService.findArticleWithAuction(id);
    }

    @GetMapping(value = "/{id}/my")
    public Article findOwnArticleWithAuction(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        return articleService.findOwnArticleWithAuction(token, id);
    }

    @PostMapping(value = "/submit")
    public Article addArticle(@RequestHeader(name = "Authorization") String token, @RequestBody ArticleAdd article) throws BadRequestException {
        return articleService.addArticle(token, article);
    }

    @PostMapping(value = "/{id}/sell")
    public Article addAuctionToArticle(@RequestHeader(name = "Authorization") String token, @PathVariable long id, @RequestBody AuctionAdd auction) throws BadRequestException {
        return articleService.addAuctionToArticle(token, id, auction);
    }

    @DeleteMapping(value = "/{id}/delete")
    public void deleteArticle(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        articleService.deleteArticle(token, id);
    }

    @PostMapping(value = "/{id}/remove")
    public void deleteAuctionFromArticle(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        articleService.deleteAuctionFromArticle(token, id);
    }
}
