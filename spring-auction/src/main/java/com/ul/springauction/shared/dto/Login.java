package com.ul.springauction.shared.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO de connexion de l'utilisateur
 */
public class Login implements DtoObject{

    @NotNull(message = "Veuillez entrer votre login")
    @Size(min = 1, max = 255, message = "Votre login fait au moins 1 caractere")
    private String login;

    @NotNull(message = "Veuillez entrer votre mot de passe")
    @Size(min = 1, max = 255, message = "Votre mot de passe fait au moins 1 caractere")
    private String pass;

    //default constructor for JSON parsing
    public Login(){}

    public Login(String login, String pass){
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

    public void setPass(String password) {
        this.pass = password;
    }
}
