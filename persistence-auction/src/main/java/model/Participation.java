package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double price;

    @ManyToOne(targetEntity = User.class)
    private User bidder;

    @ManyToOne(targetEntity = Article.class)
    private Article article;

    public Participation(long id, double price, User bidder, Article article) {
        this.id = id;
        this.price = price;
        this.bidder = bidder;
        this.article = article;
    }

    public Participation(double price, User bidder, Article article) {
        this.price = price;
        this.bidder = bidder;
        this.article = article;
    }

    public Participation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
