package com.ul.springauction.services.article;

import com.ul.springauction.DAO.ArticleRepository;
//import com.ul.springauction.model.Article;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepo;

    public Iterable<Article> findAll(){
        return articleRepo.findAll();
    }
}
