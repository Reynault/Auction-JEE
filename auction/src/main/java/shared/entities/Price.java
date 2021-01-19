package shared.entities;

public class Price implements Entity {

    private double price;

    public Price(double price) {
        this.price = price;
    }

    public Price() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
