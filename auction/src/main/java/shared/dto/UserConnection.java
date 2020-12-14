package shared.dto;

import java.io.Serializable;

/**
 * Data Transfert Object lorsqu'un utilisateur veut se connecter
 */
public class UserConnection implements Serializable {

    private String login;
    private String pass;

    public UserConnection(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
