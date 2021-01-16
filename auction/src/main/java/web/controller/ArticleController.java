package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.article.ArticleServiceLocal;
import shared.dto.ArticleCreation;
import shared.params.SearchParams;
import web.config.authentification.Secured;

/**
 * Controleur utilise pour recuperer et gerer les requetes qui concernent les
 * articles
 */
@Path("/articles")
public class ArticleController {

    @EJB
    private ArticleServiceLocal service;

    @GET
    @Secured
    @Path("my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMine(@HeaderParam("login") String login) {
        return Response.ok(service.getMine(login)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") long id) {
        return Response.ok(service.getOne(id)).build();
    }

    @POST
    @Secured
    @Path("submit")
    public Response submitArticle(
            @HeaderParam("login") String login,
            @NotNull(message = "Veuillez fournir les informations n�c�ssaires pour cr�er l'article")
            @Valid ArticleCreation article) {
        return Response.ok(service.postOne(article, login)).build();
    }

    @DELETE
    @Secured
    @Path("delete/{id}")
    public Response deleteArticle(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        service.delete(id, login);
        return Response.noContent().build();
    }

    @PUT
    @Secured
    @Path("remove/{id}")
    public Response removeFromMarket(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        service.removeFromMarket(id, login);
        return Response.noContent().build();
    }

    @GET
    public Response getAll(@BeanParam SearchParams search) {
        return Response.ok(service.getAll(search)).build();
    }
}
