package model;

import enumeration.DeliveryStep;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DELIVERY")
public class Delivery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private DeliveryStep step;

    private String message;

    private double price;

    @OneToOne(targetEntity = ArticleToDeliver.class, cascade = CascadeType.ALL)
    private ArticleToDeliver article;

    public Delivery(long id, DeliveryStep step, String message, double price, ArticleToDeliver article) {
        this.id = id;
        this.step = step;
        this.message = message;
        this.price = price;
        this.article = article;
    }

    public Delivery(DeliveryStep step, String message, double price, ArticleToDeliver article) {
        this.step = step;
        this.message = message;
        this.price = price;
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

    public DeliveryStep getState() {
        return step;
    }

    public void setState(DeliveryStep step) {
        this.step = step;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DeliveryStep getStep() {
        return step;
    }

    public void setStep(DeliveryStep step) {
        this.step = step;
    }

    public ArticleToDeliver getArticle() {
        return article;
    }

    public void setArticle(ArticleToDeliver article) {
        this.article = article;
    }

}
