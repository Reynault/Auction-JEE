package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double price;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Article.class)
    private Article article;

    public Delivery(long id, double price, User user, Article article) {
        this.id = id;
        this.price = price;
        this.user = user;
        this.article = article;
    }

    public Delivery(double price, User user, Article article) {
        this.price = price;
        this.user = user;
        this.article = article;
    }

    public Delivery() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
