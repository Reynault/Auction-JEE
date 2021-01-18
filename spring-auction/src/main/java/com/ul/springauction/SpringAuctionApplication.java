package com.ul.springauction;

import com.ul.springauction.shared.init.DatabaseService;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class, Category.class, Delivery.class, Participation.class, Address.class, Auction.class}
)
public class SpringAuctionApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringAuctionApplication.class);
    @Autowired
    private DatabaseService initDatabase;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(){
        return args -> {
            log.info("----Starting initialize database---");
            //initDatabase.init();
            log.info("----Ending initialize database---");
        };
    }

}
