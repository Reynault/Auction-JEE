package com.ul.springauction.services.article;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.CategoryRepository;
import com.ul.springauction.shared.response.Response;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    public Iterable<Article> findAll(){
        return articleRepo.findAll();
    }

    /*public Response save (Article article){
        articleRepo.save(article);
    }*/
}
