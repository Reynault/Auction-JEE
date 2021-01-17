/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.init;

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
 * Singleton qui va initialiser les donnÃ©es de test
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

        Category c1 = new Category("jeux");
        Category c2 = new Category("retro");
        Category c3 = new Category("nintendo");
        Category c4 = new Category("mauvais");
        Category c5 = new Category("plateforme");

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);

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

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);

        User u1 = new User(
                "bob",
                crypt.hash("pass"),
                "bob",
                "ross",
                null,
                new ArrayList(),
                Arrays.asList(a1, a2)
        );

        User u2 = new User(
                "bob1",
                crypt.hash("pass"),
                "bob",
                "ross",
                null,
                new ArrayList(),
                Arrays.asList(a3, a4)
        );

        em.persist(u1);
        em.persist(u2);

        System.out.println("----DATABASE INITIALIZED----");
    }
}
