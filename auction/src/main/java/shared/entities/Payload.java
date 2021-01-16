package shared.entities;

public class Payload implements Entity {

    private String token;

    public Payload(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
