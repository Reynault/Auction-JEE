package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import web.config.customValidator.ValidDate;

public class AuctionCreation implements Serializable {

    @NotNull(message = "Veuillez fournir un id d'article")
    private long article_id;

    @NotNull(message = "Veuillez fournir un prix de base")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double firstPrice;

    @ValidDate(message = "Veuillez fournir une date valide")
    private String timeLimit;

    public AuctionCreation(long article_id, double firstPrice, String timeLimit) {
        this.article_id = article_id;
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
    }

    public AuctionCreation() {
    }

    public long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(long article_id) {
        this.article_id = article_id;
    }

    public double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

}
