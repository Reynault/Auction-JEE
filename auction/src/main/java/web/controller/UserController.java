package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.user.UserServiceLocal;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import web.config.authentification.Secured;

@Path("/")
public class UserController {

    @EJB
    private UserServiceLocal service;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @NotNull(message = "Veuillez fournir vos identifiants de connexion")
            @Valid UserConnection userConnection) {
        return Response.ok(service.login(userConnection)).build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(
            @NotNull(message = "Veuillez fournir vos données d'inscription")
            @Valid UserInscription userInscription) {
        return Response.ok(service.register(userInscription)).build();
    }

    @GET
    @Secured
    @Path("fetchAddress")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAddress(@HeaderParam("login") String login) {
        return Response.ok(service.getAddress(login)).build();
    }
}
