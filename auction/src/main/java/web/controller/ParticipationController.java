package web.controller;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import web.config.authentification.Secured;

@Path("/participation")
public class ParticipationController {

    @GET
    @Secured
    @Path("my")
    public Response getMyParticipations(@HeaderParam("login") String login) {
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("{id}/my")
    public Response getOneParticipation(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("{id}")
    public Response participate(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        return Response.ok().build();
    }
}
