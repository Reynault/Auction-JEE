package com.ul.springauction.shared.auth;

import java.io.Serializable;

public class AuthResponse implements Serializable {

    private final String JWT;

    public AuthResponse(String value){
        JWT = value;
    }

    public String getJWT(){
        return JWT;
    }
}
