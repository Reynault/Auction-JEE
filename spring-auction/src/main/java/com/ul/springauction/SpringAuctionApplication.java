package com.ul.springauction;

import model.Article;
import model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class}
)
public class SpringAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

}
