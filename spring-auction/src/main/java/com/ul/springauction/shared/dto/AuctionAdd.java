package com.ul.springauction.shared.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO pour la création d'une enchère associée à un objet
 */
public class AuctionAdd implements DtoObject {

    @NotNull(message = "Veuillez fournir un prix de base")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double firstPrice;

    @NotNull(message = "Veuillez fournir une date limite")
    @Size(min = 10, max = 10, message = "le format de la date est YYYY-MM-DD")
    private String timeLimit;

    public AuctionAdd(){}

    public AuctionAdd(double firstPrice, String timeLimit){
        this.firstPrice = firstPrice;
        this.timeLimit = timeLimit;
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
