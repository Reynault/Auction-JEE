package com.ul.springauction.shared.init;

import com.ul.springauction.DAO.repository.*;
import enumeration.PromotionType;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Service to initialize database
 * When we launch the Spring application
 */
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
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private PromotionRepository promotionRepository;

    public void init() throws ParseException {
        // Remove everything inside the database
        userRepository.deleteAll();
        articleRepository.deleteAll();
        categoryRepository.deleteAll();
        auctionRepository.deleteAll();

        // Creation of categories and saving them
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


        // Creation of articles
        Article a1 = new Article(
                "Zelda: Ocarina of time",
                "The Legend of Zelda: Ocarina of Time is an action-adventure game developed and published by Nintendo",
                Arrays.asList(c1, c2, c3),
                null
        );

        Article a2 = new Article(
                "Bubsy 3D",
                "Bubsy 3D, ceci en est une description",
                Arrays.asList(c1, c2, c4),
                null
        );

        Article a3 = new Article(
                "Crash Bandicoot",
                "Crash Bandicoot est une série de jeux vidéo de plates-formes",
                Arrays.asList(c1, c2, c5),
                null
        );

        Article a4 = new Article(
                "Guitare Electrique de Johnny Hallyday",
                "Magnifique guitare electrique ayant appartenue à Johnny Hallyday !",
                Arrays.asList(c1, c2, c4),
                null
        );

        // Creation of users
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

        User u3 = new User(
                "user3",
                bCryptPasswordEncoder.encode("azerty"),
                "Pat",
                "Och",
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        // Creation of participations
        Participation par1 = new Participation(500, u1);
        Participation par2 = new Participation(550, u2);
        Participation par3 = new Participation(600, u3);
        Participation par4 = new Participation(450, u3);

        participationRepository.save(par1);
        participationRepository.save(par2);
        participationRepository.save(par3);
        participationRepository.save(par4);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Creation of some auctions
        Auction au1 = new Auction(
                20,
                sdf.parse("2025-12-29"),
                par3,
                Arrays.asList(par3, par2),
                a1
        );
        Auction au2 = new Auction(
                50,
                sdf.parse("2020-12-29"),
                par1,
                Arrays.asList(par1, par4),
                a3
        );

        a1.setAuction(au1);
        a3.setAuction(au2);

        articleRepository.save(a1);
        articleRepository.save(a3);

        // Creation of some promotions
        Promotion p1 = new Promotion(
                "Réduction de 100€ si l'article coûte plus de 1000€",
                Arrays.asList(
                        new Parameter(1, 500),
                        new Parameter(2, 100)
                ),
                false,
                PromotionType.HIGH_PRICE
        );
        Promotion p2 = new Promotion(
                "Réduction de 50€ si l'article coûte plus de 50€ et si l'utilisateur a plus de 2 participations",
                Arrays.asList(
                        new Parameter(1, 50),
                        new Parameter(2, 50),
                        new Parameter(3, 2)
                ),
                false,
                PromotionType.PARTICIPATION_REWARD
        );
        Promotion p3 = new Promotion(
                "Réduction de 50€ si l'article coûte plus de 50€ et si l'enchère a plus de 2 participations",
                Arrays.asList(
                        new Parameter(1, 50),
                        new Parameter(2, 20),
                        new Parameter(3, 2)
                ),
                false,
                PromotionType.SUCCESSFULL_AUCTION
        );
        promotionRepository.save(p1);
        promotionRepository.save(p2);
        promotionRepository.save(p3);
        System.out.println("Initialisation OK !");
    }
}
