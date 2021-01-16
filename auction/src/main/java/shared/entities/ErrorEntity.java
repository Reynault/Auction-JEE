package shared.entities;

import java.util.ArrayList;
import java.util.List;

public class ErrorEntity implements Entity {

    private List<String> messages;

    public ErrorEntity(List<String> message) {
        this.messages = message;
    }

    public ErrorEntity(String message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public ErrorEntity() {
    }

    public List<String> getMessage() {
        return messages;
    }

    public void setMessage(List<String> message) {
        this.messages = message;
    }

}
