package com.ul.springauction;

import com.ul.springauction.shared.init.DatabaseService;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class, Category.class, Delivery.class, Participation.class, Address.class, Auction.class, ArticleToDeliver.class}
)
@EnableScheduling
public class SpringAuctionApplication {

    @Autowired
    private DatabaseService initDatabase;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(){
        return args -> {
            initDatabase.init();
        };
    }
}
