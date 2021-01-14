package web.config.exceptionMapper;

import javax.json.bind.JsonbException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import shared.entities.ErrorEntity;

public class JSONBExceptionMapper implements ExceptionMapper<JsonbException> {

    @Override
    public Response toResponse(JsonbException jpe) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorEntity("Les donn�es en JSON fournies sont mal form�es"))
                .build();
    }
}
