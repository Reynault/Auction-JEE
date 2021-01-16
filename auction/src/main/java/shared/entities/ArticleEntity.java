package shared.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import model.Article;

public class ArticleEntity implements Entity {

    private long id;
    private String name;
    private String description;
    private double firstPrice;
    @JsonProperty("caqsdqsdtegories")
    private List<String> categories;
    private Date timeLimit;
    private double bestPrice;

    public ArticleEntity(long id, String name, String description, double firstPrice, List<String> categories, Date timeLimit, double bestPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstPrice = firstPrice;
        this.categories = categories;
        this.timeLimit = timeLimit;
        this.bestPrice = bestPrice;
    }

    public static ArticleEntity convertArticleToEntity(Article article) {
        return new ArticleEntity(
                article.getId(),
                article.getName(),
                article.getDescription(),
                article.getFirstPrice(),
                article.getCategoriesAsStrings(),
                article.getTimeLimit(),
                article.getBestPrice()
        );
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

    public Date getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Date timeLimit) {
        this.timeLimit = timeLimit;
    }

    public double getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(double bestPrice) {
        this.bestPrice = bestPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
