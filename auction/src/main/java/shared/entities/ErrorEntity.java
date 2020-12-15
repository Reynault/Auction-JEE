package shared.entities;

import java.io.Serializable;
import java.util.List;

public class ErrorEntity implements Serializable {

    private List<String> messages;

    public ErrorEntity(List<String> message) {
        this.messages = message;
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
