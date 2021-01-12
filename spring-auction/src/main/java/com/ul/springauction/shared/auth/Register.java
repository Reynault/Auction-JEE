package com.ul.springauction.shared.auth;

import java.io.Serializable;

public class Register implements Serializable {

    private String login;
    private String pass;
    private String name;
    private String lastname;

    //default constructor for JSON parsing
    public Register(){}

    public Register(String login, String pass, String name, String lastname){
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = name;
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
