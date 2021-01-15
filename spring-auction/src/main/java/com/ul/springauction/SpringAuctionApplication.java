package com.ul.springauction;

import model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
        basePackages = "com.company.persistence-auction.src.main.java.model",
        basePackageClasses = {User.class, Article.class, Category.class, Delivery.class, Participation.class, Address.class}
)
public class SpringAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuctionApplication.class, args);
    }

}
