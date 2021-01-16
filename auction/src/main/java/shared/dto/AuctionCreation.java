package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import web.config.customValidator.ValidDate;

public class AuctionCreation implements Serializable {

    @NotNull(message = "Veuillez fournir un prix de base")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double firstPrice;

    @ValidDate(message = "Veuillez fournir une date valide")
    private String timeLimit;

    public AuctionCreation(double firstPrice, String timeLimit) {
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
    }

    public AuctionCreation() {
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
