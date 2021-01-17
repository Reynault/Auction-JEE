package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import service.participation.ParticipateServiceLocal;
import shared.dto.UserParticipate;
import web.config.authentification.Secured;

@Path("/participation")
public class ParticipationController {

    @EJB
    private ParticipateServiceLocal service;

    @GET
    @Secured
    @Path("my")
    public Response getMyParticipatedArticles(@HeaderParam("login") String login) {
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("{id}/my")
    public Response getOneParticipatedArticle(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("{id}")
    public Response participate(
            @HeaderParam("login") String login,
            @PathParam("id") long id,
            @NotNull(message = "Veuillez fournir vos données de participation")
            @Valid UserParticipate participation) {
        return Response.ok(service.participate(participation, login, id)).build();
    }
}
