package com.ul.springauction.services.article;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.UserRepository;
import com.ul.springauction.services.auth.JwtUtil;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
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

    public Article findArticlesWithAuction(long id) throws BadRequestException {
        Article a = articleRepo.findById(id);
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
}
