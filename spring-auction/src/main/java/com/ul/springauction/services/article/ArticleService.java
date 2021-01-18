package com.ul.springauction.services.article;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.UserRepository;
import com.ul.springauction.services.auth.JwtUtil;
import model.Article;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepo;

    public List<Article> findUserArticles(String token){
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        User u = userRepository.findByLogin(username);
        return u.getSold();
    }

    /*public Response save (Article article){
        articleRepo.save(article);
    }*/
}
