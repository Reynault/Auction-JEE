package com.ul.springauction.services;

import com.ul.springauction.DAO.repository.ArticleRepository;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.services.validator.DtoValidator;
import com.ul.springauction.shared.dto.ArticleAdd;
import com.ul.springauction.shared.dto.AuctionAdd;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Auction;
import model.Category;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private DtoValidator dtoValidator;

    public Article findById(long id){
        return articleRepo.findById(id);
    }

    public List<Article> findUserArticles(String token){
        User u = userService.findUser(token);
        return u.getSold();
    }

    public Article findArticleWithAuction(long id) throws BadRequestException {
        Article a = articleRepo.findById(id);
        return checkAuction(a);
    }

    public Article findOwnArticleWithAuction(String token, long id) throws BadRequestException {
        List<Article> articles = findUserArticles(token);
        for (Article a : articles){
            if (a.getId() == id){
                return checkAuction(a);
            }
        }
        throw new BadRequestException("L'article avec cet id n'est pas a cet utilisateur");
    }

    public List<Article> findArticlesByAuctions(List<Auction> auctions){
        List<Article> articles = new ArrayList<>();
        for (Auction a : auctions){
            articles.add(articleRepo.findByAuction(a));
        }
        return articles;
    }

    public Article addArticle(String token, ArticleAdd article) throws BadRequestException {
        dtoValidator.validation(article);
        List<Category> categories = categoryService.findAndAddCategories(article.getCategories());
        Article newArt = new Article(article.getName(), article.getDescription(), categories, null);
        articleRepo.save(newArt);

        User u = userService.findUser(token);
        u.getSold().add(newArt);
        userService.saveUpdatedUser(u);

        return newArt;
    }

    public Article addAuctionToArticle(String token, long id, AuctionAdd auction) throws BadRequestException {
        dtoValidator.validation(auction);
        User u = userService.findUser(token);
        Article articleUpdate = articleRepo.findById(id);
        if (u.getSold().contains(articleUpdate)){
            if (articleUpdate.getAuction() != null){
                throw new BadRequestException("Une enchere existe deja pour cet article");
            } else {
                if (!articleUpdate.isHasBeenSold()) {
                    articleUpdate.setAuction(auctionService.createAuction(auction, articleUpdate));
                    articleRepo.save(articleUpdate);
                    return articleUpdate;
                } else {
                    throw new BadRequestException("L'article a deja ete vendu");
                }
            }
        } else {
            throw new BadRequestException("L'article avec cet id n'est pas a cet utilisateur");
        }
    }

    public List<Article> getAllArticles(String name, List<String> categories){
        List<Category> categoryList = categoryService.getAllByList(categories);
        List<Article> articles = null;
        if (name == null){
            articles = (List<Article>) articleRepo.findAll();
        } else {
            articles = articleRepo.findByNameContaining(name);
        }

        if (categoryList != null) {
            for (Category c : categoryList) {
                articles = checkCategoryAndRemove(articles, c);
            }
        }
        articles = checkAuctionAndRemove(articles);
        return articles;
    }

    public void deleteArticle(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article articleDel = articleRepo.findById(id);
        if(u.getSold().contains(articleDel)){
            u.getSold().remove(articleDel);
            userService.saveUpdatedUser(u);
            auctionService.deleteAuction(articleDel.getAuction());
            articleRepo.deleteById(articleDel.getId());
        } else {
            throw new BadRequestException("L'article avec cet id n'est pas a cet utilisateur");
        }
    }

    public void deleteAuctionFromArticle(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article article = articleRepo.findById(id);
        if(u.getSold().contains(article)){
            auctionService.deleteAuction(article.getAuction());
            article.setAuction(null);
            articleRepo.save(article);
        } else {
            throw new BadRequestException("L'article avec cet id n'est pas a cet utilisateur");
        }
    }


    public Article checkAuction(Article a) throws BadRequestException {
        if (a.getAuction() == null){
            throw new BadRequestException("L'article n'est pas aux encheres");
        } else {
            if (a.getAuction().getTimeLimit().before(Date.from(Instant.now()))){
                throw new BadRequestException("L'article n'est plus aux encheres");
            } else {
                return a;
            }
        }
    }

    public List<Article> checkAuctionAndRemove(List<Article> articles){
        List<Article> remove = new ArrayList<>();
        for (Article a : articles){
            if (a.getAuction() == null || a.isHasBeenSold()){
                remove.add(a);
            } else {
                if (a.getAuction().getTimeLimit().before(Date.from(Instant.now()))) {
                    remove.add(a);
                }
            }
        }
        articles.removeAll(remove);
        return articles;
    }

    public List<Article> checkCategoryAndRemove(List<Article> articles, Category c){
        articles.removeIf(a -> !a.getCategories().contains(c));
        return articles;
    }

    public void saveUpdatedArticle(Article a){
        articleRepo.save(a);
    }
}
