package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * POJO for an article to map with the BDD
 */
@Entity
@Table
@NamedQueries({
        @NamedQuery(
                name = "Article.findAll",
                query = "SELECT a FROM Article a"
        )
})
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double firstPrice;
    private LocalDate timeLimit;

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
