package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfert Object lorsqu'un utilisateur veut se connecter
 */
public class UserConnection implements Serializable {

    @NotNull(message = "Veuillez fournir un login")
    @Size(min = 1, max = 255, message = "Le login ne doit pas être vide et doit faire moins de 255 caractères")
    private String login;

    @NotNull(message = "Veuillez fournir un mot de passe")
    @Size(min = 1, max = 255, message = "Le mot de passe ne doit pas être vide et doit faire moins de 255 caractères")
    private String pass;

    public UserConnection(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public UserConnection() {
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
