package web.config.exceptionMapper;

import java.util.ArrayList;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import shared.entities.ErrorEntity;

/**
 * Cette classe permet de r�cup�rer les exceptions de type
 * ConstraintViolationException qui sont lanc�es lorsque une ou plusieurs
 * contraintes sur les DTO n'ont pas �t� respect�es
 */
public class ConstraintMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private ErrorEntity prepareMessage(ConstraintViolationException exception) {
        ArrayList<String> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(cv -> {
            errors.add(cv.getMessage());
        });
        return new ErrorEntity(errors);
    }
}
