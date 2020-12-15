package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.auth.AuthServiceLocal;
import shared.dto.UserConnection;
import shared.dto.UserInscription;

/**
 * Controleur utilise pour gerer l'authentification et l'inscription
 *
 * Documentation utilisee pour mettre en place l'authentification en java:
 *
 * https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 * https://developer.okta.com/blog/2018/10/31/jwts-with-java
 */
@Path("/")
public class AuthController {

    @EJB
    private AuthServiceLocal service;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(
            @NotNull(message = "Veuillez fournir vos identifiants de connexion")
            @Valid UserConnection userConnection) {
        return Response.ok(service.login(userConnection)).build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(
            @NotNull(message = "Veuillez fournir vos données d'inscription")
            @Valid UserInscription userInscription) {
        return Response.ok(service.register(userInscription)).build();
    }
}
