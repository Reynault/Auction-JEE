package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * POJO for an article to map with the BDD
 */
@Entity
@Table(name = "ARTICLE")
@NamedQueries({
    @NamedQuery(
            name = "Article.findAll",
            query = "SELECT a FROM Article a"
    ),
    @NamedQuery(
            name = "Article.findOne",
            query = "SELECT a FROM Article a WHERE a.id = :id"
    ),
    @NamedQuery(
            name = "Article.delete",
            query = "DELETE FROM Article a WHERE a.id = :id"
    )
})
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(targetEntity = Category.class, cascade = {
        CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Category> categories;

    @OneToOne(targetEntity = Auction.class, cascade = CascadeType.ALL)
    @JoinColumn(nullable = true)
    private Auction auction;

    private boolean hasBeenSold;

    public Article(long id, String name, String description, List<Category> categories, Auction auction) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.auction = auction;
        this.hasBeenSold = false;
    }

    public Article(String name, String description, List<Category> categories, Auction auction) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.auction = auction;
        this.hasBeenSold = false;
    }

    public Article() {
    }

    @JsonIgnore
    public static List<Category> getStringAsCategories(List<String> categories) {
        ArrayList<Category> c = new ArrayList();
        categories.forEach(s -> {
            c.add(new Category(s));
        });
        return c;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public boolean isHasBeenSold() {
        return hasBeenSold;
    }

    public void setHasBeenSold(boolean hasBeenSold) {
        this.hasBeenSold = hasBeenSold;
    }

}
