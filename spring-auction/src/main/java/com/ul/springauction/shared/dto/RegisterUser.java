package com.ul.springauction.shared.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * DTO d'ajout d'un utilisateur
 */
public class RegisterUser implements DtoObject {

    @NotNull(message = "Veuillez donner un login")
    @Size(min = 1, max = 255, message = "La taille du pseudo est au moins de 1 caractere")
    private String login;

    @NotNull(message = "Veuillez donner un mot de passe")
    @Size(min = 1, max = 255, message = "Le mot de passe doit faire au minimum 1 caractere")
    private String pass;

    @NotNull(message = "Veuiller donner votre prenom")
    @Size(min = 1, max = 255, message = "Votre prenom doit au moins faire 1 caractere")
    private String name;

    @NotNull(message = "Veuillez donner votre nom")
    @Size(min = 1, max = 255, message = "Votre nom doit au moins faire 1 caractere")
    private String lastname;

    @Valid
    private RegisterAddress address;

    //default constructor for JSON parsing
    public RegisterUser(){}

    public RegisterUser(String login, String pass, String name, String lastname, RegisterAddress address){
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
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

    public Optional<RegisterAddress> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(RegisterAddress address) {
        this.address = address;
    }
}
