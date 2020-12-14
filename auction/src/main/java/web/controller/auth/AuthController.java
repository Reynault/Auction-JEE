package web.controller.auth;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Stateless
@Path("/")
public class AuthController implements AuthControllerLocal {

    @EJB
    private AuthServiceLocal service;

    @Override
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(UserConnection userConnection) {
        return Response.ok(service.login(userConnection)).build();
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("register")
    public Response register(UserInscription userInscription) {
        return Response.ok(service.register(userInscription)).build();
    }
}
