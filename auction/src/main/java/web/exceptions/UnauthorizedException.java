package web.exceptions;

import javax.ws.rs.WebApplicationException;

public class UnauthorizedException extends WebApplicationException {

    public UnauthorizedException() {
        super("Autorisation requise");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
