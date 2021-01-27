package com.ul.springauction.shared.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'un retour d'erreur si ces dernières sont déclanchées
 */
public class ErrorResponse implements Response {

    private List<String> message;

    public ErrorResponse(List<String> message){
        this.message = message;
    }

    public ErrorResponse(String message){
        this.message = new ArrayList<>();
        this.message.add(message);
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }
}
