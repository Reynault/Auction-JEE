package com.ul.springauction.shared.init;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.CategoryRepository;
import com.ul.springauction.DAO.UserRepository;
import model.Article;
import model.Category;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class DatabaseService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public String init(){
        userRepository.deleteAll();
        articleRepository.deleteAll();
        categoryRepository.deleteAll();

        Category c1 = new Category("jeux");
        Category c2 = new Category("retro");
        Category c3 = new Category("nintendo");
        Category c4 = new Category("mauvais");
        Category c5 = new Category("plateforme");

        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);
        categoryRepository.save(c5);

        Article a1 = new Article(
                "Zelda: Ocarina of time",
                "The Legend of Zelda: Ocarina of Time is an action-adventure game developed and published by Nintendo for the Nintendo 64.",
                Arrays.asList(c1, c2, c3),
                null
        );

        Article a2 = new Article(
                "Bubsy 3D",
                "Bubsy 3D (also Bubsy 3D: Furbitten Planet or Bubsy is 3D in \"Furbitten Planet\") is a platform video game developed by Eidetic and published by Accolade for the PlayStation video game console.",
                Arrays.asList(c1, c2, c4),
                null
        );

        Article a3 = new Article(
                "Crash Bandicoot",
                "Crash Bandicoot est une série de jeux vidéo de plates-formes créée par Andy Gavin et Jason Rubin, débutée en 1996 sur PlayStation.",
                Arrays.asList(c1, c2, c5),
                null
        );

        Article a4 = new Article(
                "Guitare Electrique de Johnny Hallyday",
                "Magnifique guitare electrique ayant appartenue à Johnny Hallyday !",
                Arrays.asList(c1, c2, c4),
                null
        );

        User u1 = new User(
                "bob",
                bCryptPasswordEncoder.encode("pass"),
                "bob",
                "ross",
                null,
                new ArrayList(),
                Arrays.asList(a1, a2)
        );

        User u2 = new User(
                "bob1",
                bCryptPasswordEncoder.encode("pass"),
                "bob",
                "ross",
                null,
                new ArrayList(),
                Arrays.asList(a3, a4)
        );

        userRepository.save(u1);
        userRepository.save(u2);
        return "Initialisation OK !";
    }
}
