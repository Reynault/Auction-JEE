package web.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import shared.entities.ErrorEntity;

public class BadValuesException extends WebApplicationException {

    public BadValuesException() {
        super(Response.status(Status.BAD_REQUEST)
                .entity(new ErrorEntity("Mauvaises valeurs"))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }

    public BadValuesException(String message) {
        super(Response.status(Status.BAD_REQUEST)
                .entity(new ErrorEntity(message))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
