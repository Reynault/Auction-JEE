package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfert Object lorsqu'un utilisateur veut s'inscrire sur le site web
 */
public class UserInscription implements Serializable {

    @NotNull(message = "Veuillez fournir un login")
    @Size(min = 1, max = 255, message = "Le login ne doit pas être vide et doit faire moins de 255 caractères")
    private String login;
    @NotNull(message = "Veuillez fournir un mot de passe")
    @Size(min = 1, max = 255, message = "Le mot de passe ne doit pas être vide et doit faire moins de 255 caractères")
    private String pass;
    @NotNull(message = "Veuillez fournir un prénom")
    @Size(min = 1, max = 255, message = "Le prénom ne doit pas être vide et doit faire moins de 255 caractères")
    private String name;
    @NotNull(message = "Veuillez fournir un nom")
    @Size(min = 1, max = 255, message = "Le nom ne doit pas être vide et doit faire moins de 255 caractères")
    private String lastname;

    public UserInscription(String login, String pass, String name, String lastname) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
    }

    public UserInscription() {
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
