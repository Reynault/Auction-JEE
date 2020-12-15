package web.config;

import java.util.Arrays;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import shared.entities.ErrorEntity;

public class JSONParsingMapper implements ExceptionMapper<JsonParsingException> {

    @Override
    public Response toResponse(JsonParsingException jpe) {
        return Response.status(Status.BAD_REQUEST)
                .entity(new ErrorEntity(
                        Arrays.asList("Les données en JSON fournies sont mal formées"))
                ).build();
    }
}
