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

    private double price;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(targetEntity = ArticleToDeliver.class, cascade = CascadeType.ALL)
    private ArticleToDeliver article;

    public Delivery(long id, DeliveryStep step, double price, Address address, ArticleToDeliver article) {
        this.id = id;
        this.step = step;
        this.price = price;
        this.address = address;
        this.article = article;
    }

    public Delivery(DeliveryStep step, double price, Address address, ArticleToDeliver article) {
        this.step = step;
        this.price = price;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
