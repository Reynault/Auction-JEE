package web.controller.article;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import service.article.ArticleServiceLocal;

/**
 * Controleur utilise pour recuperer et gerer les requetes qui concernent les
 * articles
 */
@Stateless
@Path("/articles")
public class ArticleController implements ArticleControllerLocal {

    @EJB
    private ArticleServiceLocal service;

    @GET
    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }
}
