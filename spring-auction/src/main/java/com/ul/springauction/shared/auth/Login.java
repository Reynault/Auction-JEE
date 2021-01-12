package com.ul.springauction.shared.auth;

import java.io.Serializable;

public class Login implements Serializable {

    private String login;
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
