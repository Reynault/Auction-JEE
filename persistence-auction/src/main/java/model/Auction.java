package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AUCTION")
@NamedQueries({
    @NamedQuery(
            name = "Auction.findOne",
            query = "SELECT a FROM Auction a WHERE a.article.id = :id"
    ),
    @NamedQuery(
            name = "Auction.delete",
            query = "DELETE FROM Auction a WHERE a.article.id = :id"
    )
})
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double firstPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.DATE)
    private Date timeLimit;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToOne(targetEntity = Participation.class, cascade = CascadeType.ALL)
    private Participation best;

    @OneToOne(targetEntity = Delivery.class)
    private Delivery deliveries;

    @JsonIgnore
    @OneToOne(targetEntity = Article.class)
    private Article article;

    public Auction(long id, double firstPrice, Date timeLimit, List<Participation> participations, Participation best, Delivery deliveries, Article article) {
        this.id = id;
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
        this.participations = participations;
        this.best = best;
        this.deliveries = deliveries;
        this.article = article;
    }

    public Auction(double firstPrice, Date timeLimit, List<Participation> participations, Participation best, Delivery deliveries, Article article) {
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
        this.participations = participations;
        this.best = best;
        this.deliveries = deliveries;
        this.article = article;
    }

    public Participation getBest() {
        return best;
    }

    public Auction() {
    }

    public void setBest(Participation best) {
        this.best = best;
    }

    public Date getRemainingTime() {
        return this.timeLimit;
    }

    public double getBestPrice() {
        if (best == null) {
            return firstPrice;
        } else {
            return best.getPrice();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public Date getTimeLimit() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return timeLimit;
    }

    public void setTimeLimit(Date timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public Delivery getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Delivery deliveries) {
        this.deliveries = deliveries;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
