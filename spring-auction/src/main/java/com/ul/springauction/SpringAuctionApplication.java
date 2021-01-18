package com.ul.springauction;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.DAO.CategoryRepository;
import com.ul.springauction.DAO.UserRepository;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class, Category.class, Delivery.class, Participation.class, Address.class, Auction.class}
)
public class SpringAuctionApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringAuctionApplication.class);
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(ArticleRepository articleRepository, UserRepository userRepository, CategoryRepository categoryRepository){
        return args -> {
            log.info("----Starting initialize database---");

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

            log.info("----Ending initialize database---");
        };
    }

}
