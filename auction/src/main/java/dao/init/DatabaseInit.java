/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.init;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Article;
import model.Category;
import model.User;
import service.crypt.CryptServiceLocal;

/**
 * Singleton qui va initialiser les données de test
 */
@Singleton
@Startup
public class DatabaseInit {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @EJB
    private CryptServiceLocal crypt;

    @PostConstruct
    public void init() {
        System.out.println("----INITIALIZING DATABASE----");

        User u1 = new User(
                "bob",
                crypt.hash("pass"),
                "bob",
                "ross",
                null,
                new ArrayList(),
                new ArrayList(),
                new ArrayList()
        );

        em.persist(u1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Category c1 = new Category("jeux");
        Category c2 = new Category("retro");
        Category c3 = new Category("nintendo");
        Category c4 = new Category("mauvais");

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);

        Article a1 = new Article(
                "Zelda: Ocarina of time",
                "The Legend of Zelda: Ocarina of Time is an action-adventure game developed and published by Nintendo for the Nintendo 64. It was released in Japan and North America in November 1998, and in PAL regions the following month. Ocarina of Time is the fifth game in The Legend of Zelda series, and the first with 3D graphics.\n"
                + "\n"
                + "It was developed by Nintendo EAD, led by five directors including Eiji Aonuma and Yoshiaki Koizumi, produced by series co-creator Shigeru Miyamoto, and written by Kensuke Tanabe. Developed concurrently with Super Mario 64 and Mario Kart 64, it was initially intended as a 64DD disk and as a console launch game, but was ultimately delayed and released in cartridge format instead. Veteran Zelda series composer Koji Kondo created the musical score.",
                u1,
                Arrays.asList(c1, c2, c3),
                null
        );

        Article a2 = new Article(
                "Bubsy 3D",
                "Bubsy 3D (also Bubsy 3D: Furbitten Planet or Bubsy is 3D in \"Furbitten Planet\") is a platform video game developed by Eidetic and published by Accolade for the PlayStation video game console. A Sega Saturn version was developed but never released. It is the fourth game in the Bubsy series as well as the first Bubsy game in 3D. The game was released on November 25, 1996 in North America and in August 1997 in Europe. The game's complete name is a play on words in reference to Forbidden Planet, a 1956 sci-fi film. The game follows Bubsy, an orange bobcat and the central character of the Bubsy series, who must stop a race of aliens known as the Woolies from stealing all of the Earth's yarn by traveling across their home planet of Rayon and collecting rocket pieces and atoms in order to build a rocket ship and return safely to Earth.",
                u1,
                Arrays.asList(c1, c2, c4),
                null
        );

        em.merge(a1);
        em.merge(a2);
        System.out.println("----DATABASE INITIALIZED----");
    }
}
