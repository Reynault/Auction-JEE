package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARTICIPATION")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double price;

    @ManyToOne(targetEntity = User.class)
    private User bidder;

    @JsonIgnore
    @ManyToOne(targetEntity = Auction.class)
    private Auction auction;

    public Participation(long id, double price, User bidder, Auction auction) {
        this.id = id;
        this.price = price;
        this.bidder = bidder;
        this.auction = auction;
    }

    public Participation(double price, User bidder, Auction auction) {
        this.price = price;
        this.bidder = bidder;
        this.auction = auction;
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

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

}
