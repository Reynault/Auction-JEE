package shared.entities;

import java.io.Serializable;

public class Payload implements Serializable, Entity {

    private String login;
    private String token;
    private String exp;

    public Payload(String login, String token, String exp) {
        this.login = login;
        this.token = token;
        this.exp = exp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
