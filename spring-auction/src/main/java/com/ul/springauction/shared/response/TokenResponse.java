package com.ul.springauction.shared.response;

/**
 * Define a response type when a token is return
 */
public class TokenResponse implements Response {

    private final String token;

    public TokenResponse(String value){
        token = value;
    }

    public String getToken(){
        return token;
    }
}
