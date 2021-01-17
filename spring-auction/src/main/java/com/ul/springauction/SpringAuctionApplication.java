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

            Category c1 = new Category("jeux");
            Category c2 = new Category("retro");
            Category c3 = new Category("nintendo");
            Category c4 = new Category("mauvais");

            categoryRepository.save(c1);
            categoryRepository.save(c2);
            categoryRepository.save(c3);
            categoryRepository.save(c4);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Article a1 = new Article(
                    "Zelda: Ocarina of time",
                    "Ceci est la description de Zelda OT",
                    Arrays.asList(c1, c2, c3),
                    null
            );
            Article a2 = new Article(
                    "Bubsy 3D",
                    "Ceci est la description de Bubsy 3D",
                    Arrays.asList(c1, c2, c4),
                    null
            );
            articleRepository.save(a1);
            articleRepository.save(a2);

            User u = new User("bob", bCryptPasswordEncoder.encode("pass"), "bob", "ross", null,
                    new ArrayList<>(), Arrays.asList(a1, a2));

            userRepository.save(u);

            log.info("----Ending initialize database---");
        };
    }

}
