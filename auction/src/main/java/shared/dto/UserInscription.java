package shared.dto;

import java.io.Serializable;

/**
 * Data Transfert Object lorsqu'un utilisateur veut s'inscrire sur le site web
 */
public class UserInscription implements Serializable {

    private String login;
    private String pass;
    private String name;
    private String lastname;

    public UserInscription(String login, String pass, String name, String lastname) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
