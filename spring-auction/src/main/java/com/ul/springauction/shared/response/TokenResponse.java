package com.ul.springauction.shared.response;

/**
 * Définition d'une réponse possèdant un JWT
 */
public class TokenResponse implements Response {

    private String token;

    public TokenResponse(String value){
        token = value;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
