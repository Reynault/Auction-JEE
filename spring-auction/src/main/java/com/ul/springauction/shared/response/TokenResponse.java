package com.ul.springauction.shared.response;

/**
 * Define a response type when a token is return
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
