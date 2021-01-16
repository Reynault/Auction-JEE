package com.ul.springauction;

import com.ul.springauction.DAO.ArticleRepository;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class, Category.class, Delivery.class, Participation.class, Address.class, Auction.class}
)
public class SpringAuctionApplication {

    /*private static final Logger log = LoggerFactory.getLogger(SpringAuctionApplication.class);
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner initDatabase(ArticleRepository articleRepository, UserRepository userRepository){
        return args -> {

            //EntityManager em = Persistence.createEntityManagerFactory("default").createEntityManager();
            log.info("----Starting initialize database---");

            User u = new User("bob", bCryptPasswordEncoder.encode("pass"), "bob", "ross", null,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            userRepository.save(u);

            Category c1 = new Category("jeux");
            Category c2 = new Category("retro");
            Category c3 = new Category("nintendo");
            Category c4 = new Category("mauvais");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Article a1 = new Article(
                    "Zelda: Ocarina of time",
                    "The Legend of Zelda: Ocarina of Time is an action-adventure game developed and published by Nintendo for the Nintendo 64. It was released in Japan and North America in November 1998, and in PAL regions the following month. Ocarina of Time is the fifth game in The Legend of Zelda series, and the first with 3D graphics.\n"
                            + "\n"
                            + "It was developed by Nintendo EAD, led by five directors including Eiji Aonuma and Yoshiaki Koizumi, produced by series co-creator Shigeru Miyamoto, and written by Kensuke Tanabe. Developed concurrently with Super Mario 64 and Mario Kart 64, it was initially intended as a 64DD disk and as a console launch game, but was ultimately delayed and released in cartridge format instead. Veteran Zelda series composer Koji Kondo created the musical score.",
                    u,
                    Arrays.asList(c1, c2, c3),
                    null
            );
            Article a2 = new Article(
                    "Bubsy 3D",
                    "Bubsy 3D (also Bubsy 3D: Furbitten Planet or Bubsy is 3D in \"Furbitten Planet\") is a platform video game developed by Eidetic and published by Accolade for the PlayStation video game console. A Sega Saturn version was developed but never released. It is the fourth game in the Bubsy series as well as the first Bubsy game in 3D. The game was released on November 25, 1996 in North America and in August 1997 in Europe. The game's complete name is a play on words in reference to Forbidden Planet, a 1956 sci-fi film. The game follows Bubsy, an orange bobcat and the central character of the Bubsy series, who must stop a race of aliens known as the Woolies from stealing all of the Earth's yarn by traveling across their home planet of Rayon and collecting rocket pieces and atoms in order to build a rocket ship and return safely to Earth.",
                    u,
                    Arrays.asList(c1, c2, c4),
                    null
            );
            articleRepository.save(a1);
            articleRepository.save(a2);

            log.info("----Ending initialize database---");
        };
    }*/

}
