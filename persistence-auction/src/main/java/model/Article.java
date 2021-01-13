package model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

/**
 * POJO for an article to map with the BDD
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(
            name = "Article.findAll",
            query = "SELECT a FROM Article a"
    ),
    @NamedQuery(
            name = "Article.findOne",
            query = "SELECT a FROM Article a WHERE a.id = :id"
    )
})
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double firstPrice;
    private LocalDate timeLimit;

    @ManyToOne(targetEntity = User.class)
    private User owner;
    @OneToMany(targetEntity = Delivery.class, mappedBy = "article")
    private List<Delivery> deliveries;
    @OneToMany(targetEntity = Category.class)
    private List<Category> categories;
    @OneToMany(targetEntity = Participation.class, mappedBy = "article")
    private List<Participation> participations;
    @ManyToOne(targetEntity = Participation.class)
    private Participation best;

    public Article(long id, String name, String description, double firstPrice, LocalDate timeLimit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
    }

    public Article(String name, String description, double firstPrice, LocalDate timeLimit) {
        this.name = name;
        this.description = description;
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
    }

    public Article() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public LocalDate getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(LocalDate timeLimit) {
        this.timeLimit = timeLimit;
    }
}
