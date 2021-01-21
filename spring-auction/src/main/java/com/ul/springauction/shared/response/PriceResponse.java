package com.ul.springauction.shared.response;

public class PriceResponse implements Response {

    private double price;

    public PriceResponse(){}

    public PriceResponse(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
