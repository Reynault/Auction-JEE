package shared.entities;

import java.io.Serializable;

public class Payload implements Serializable, Entity {

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
