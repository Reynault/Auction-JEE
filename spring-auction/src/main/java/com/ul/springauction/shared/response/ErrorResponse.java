package com.ul.springauction.shared.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'un retour d'erreur si ces dernières sont déclanchées
 */
public class ErrorResponse implements Response {

    private List<String> messages;

    public ErrorResponse(List<String> messages){
        this.messages = messages;
    }

    public ErrorResponse(String message){
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
