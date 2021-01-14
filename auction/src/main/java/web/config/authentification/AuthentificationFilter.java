package web.config.authentification;

import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import service.jwt.JWTServiceLocal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthentificationFilter implements ContainerRequestFilter {

    @EJB
    private JWTServiceLocal jwt;

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String LOGIN_HEADER = "login";

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        // Recuperation de l'entête d'authentification
        String authorizationHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);
        String login = crc.getHeaderString(LOGIN_HEADER);
        // On extrait le token
        if (authorizationHeader != null) {
            String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            // Puis on le verifie
            try {
                if (!jwt.validateToken(token, login)) {
                    abortWithUnauthorized(crc);
                }
            } catch (EJBException | SignatureException e) {
                abortWithUnauthorized(crc);
            }
        } else {
            abortWithUnauthorized(crc);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext crc) {
        crc.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build());
    }
}
