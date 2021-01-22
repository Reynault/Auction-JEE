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
import shared.ErrorMessageManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service en gestion des articles
 */
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


    // Cherche un article par son id via le repository
    public Article findById(long id){
        return articleRepo.findById(id);
    }


    // Cherche les articles liés à un utilisateur
    public List<Article> findUserArticles(String token){
        User u = userService.findUser(token);
        return u.getSold();
    }


    // Cherche un article particulier et le renvoit uniquement s'il est mit aux enchères
    public Article findArticleWithAuction(long id) throws BadRequestException {
        Article a = articleRepo.findById(id);
        if (a == null){
            throw new BadRequestException(ErrorMessageManager.ARTICLE_NOT_FOUND);
        } else {
            if (checkAuctionValide(a)) {
                return a;
            }
        }
        throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
    }


    // Cherche un article en vente mit par l'utilisateur
    public Article findOwnArticleWithAuction(String token, long id) throws BadRequestException {
        List<Article> articles = findUserArticles(token);
        // On regarde parmis la liste des articles de l'utilisateur il y a celui qui l'on cherche
        for (Article a : articles){
            if (a.getId() == id){
                if (checkAuctionValide(a)){
                    return a;
                }
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            }
        }
        throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
    }


    // Cherche les articles liés à une des enchères données
    public List<Article> findArticlesByAuctions(List<Auction> auctions){
        List<Article> articles = new ArrayList<>();
        for (Auction a : auctions){
            articles.add(articleRepo.findByAuction(a));
        }
        return articles;
    }


    // Ajoute un article à la base de données
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


    // Ajoute une enchère à un article qui n'est pas encore mit aux enchères
    public Article addAuctionToArticle(String token, long id, AuctionAdd auction) throws BadRequestException {
        dtoValidator.validation(auction);
        User u = userService.findUser(token);
        Article articleUpdate = articleRepo.findById(id);
        // On regarde si l'article appartient à l'utilisateur
        if (u.getSold().contains(articleUpdate)){
            // Si l'article est pas déjà en vente
            if (articleUpdate.getAuction() != null){
                throw new BadRequestException(ErrorMessageManager.ALREADY_IN_SELL);
            } else {
                // Si l'article n'a pas déjà été acheté
                if (!articleUpdate.isHasBeenSold()) {
                    articleUpdate.setAuction(auctionService.createAuction(auction, articleUpdate));
                    articleRepo.save(articleUpdate);
                    return articleUpdate;
                } else {
                    throw new BadRequestException(ErrorMessageManager.ALREADY_SOLD);
                }
            }
        } else {
            throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }


    // Cherche tout les articles par des champs spécifiques données la requêtes
    public List<Article> getAllArticles(String name, List<String> categories){
        List<Category> categoryList = categoryService.getAllByList(categories);
        List<Article> articles = null;
        // Si aucun nom n'a été spécifier, on prend tout les articles
        if (name == null){
            articles = (List<Article>) articleRepo.findAll();
        } else {
            articles = articleRepo.findByNameContaining(name);
        }

        // Si des catégories sont données, on filtre pour ne garder que ceux correspondant
        if (categoryList != null) {
            for (Category c : categoryList) {
                articles = checkCategoryAndRemove(articles, c);
            }
        }
        articles = checkAuctionAndRemove(articles);
        return articles;
    }


    // Supprime un article appartenant à l'utilisateur
    public void deleteArticle(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article articleDel = articleRepo.findById(id);
        // On vérifie que l'article lui appartient
        if(u.getSold().contains(articleDel)){
            u.getSold().remove(articleDel);
            userService.saveUpdatedUser(u);
            auctionService.deleteAuction(articleDel.getAuction());
            articleRepo.deleteById(articleDel.getId());
        } else {
            throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }


    // Supprime une enchère associé à un article
    public void deleteAuctionFromArticle(String token, long id) throws BadRequestException {
        User u = userService.findUser(token);
        Article article = articleRepo.findById(id);
        // On vérifie que l'article lui appartient
        if(u.getSold().contains(article)){
            // L'article est bien aux enchères
            if (article.getAuction() != null){
                auctionService.deleteAuction(article.getAuction());
                article.setAuction(null);
                articleRepo.save(article);
            } else {
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            }
        } else {
            throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ARTICLE);
        }
    }


    // Vérifie que l'enchère est valide (pas inexistante et dépassée)
    public boolean checkAuctionValide(Article a) throws BadRequestException {
        if (a.getAuction() == null){
            throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
        } else {
            if (a.getAuction().getTimeLimit().before(Date.from(Instant.now()))){
                throw new BadRequestException(ErrorMessageManager.NOT_IN_SELL);
            } else {
                return true;
            }
        }
    }


    // Supprime de la liste tout les articles qui ont pas une enchère en cours
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


    // Supprime de la liste les articles qui n'ont pas la catégorie donnée
    public List<Article> checkCategoryAndRemove(List<Article> articles, Category c){
        articles.removeIf(a -> !a.getCategories().contains(c));
        return articles;
    }


    // Sauvegarde un article modifié avec son repository
    public void saveUpdatedArticle(Article a){
        articleRepo.save(a);
    }
}
