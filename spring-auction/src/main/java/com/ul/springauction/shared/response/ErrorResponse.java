package com.ul.springauction.shared.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse implements Response {

    private List<String> messages;

    public ErrorResponse(){}

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
