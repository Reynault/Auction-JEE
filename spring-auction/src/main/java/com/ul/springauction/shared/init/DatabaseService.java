package com.ul.springauction.shared.init;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.AuctionRepository;
import com.ul.springauction.DAO.CategoryRepository;
import com.ul.springauction.DAO.UserRepository;
import model.Article;
import model.Auction;
import model.Category;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
    @Autowired
    private AuctionRepository auctionRepository;

    public void init() {
        userRepository.deleteAll();
        articleRepository.deleteAll();
        categoryRepository.deleteAll();
        auctionRepository.deleteAll();

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse("2021-03-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        a1.setAuction(new Auction(30.0, d, null, new ArrayList<>(), a1));

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
        try {
            d = sdf.parse("2021-04-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        a3.setAuction(new Auction(15.0, d, null, new ArrayList<>(), a3));

        Article a4 = new Article(
                "Guitare Electrique de Johnny Hallyday",
                "Magnifique guitare electrique ayant appartenue à Johnny Hallyday !",
                Arrays.asList(c1, c2, c4),
                null
        );
        try {
            d = sdf.parse("2019-04-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        a4.setAuction(new Auction(21.0, d, null, new ArrayList<>(), a4));

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
        System.out.println("Initialisation OK !");
    }
}
