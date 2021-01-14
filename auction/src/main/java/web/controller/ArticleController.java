package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.article.ArticleServiceLocal;
import shared.dto.ArticleCreation;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }

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
            @NotNull(message = "Veuillez fournir les informations nécéssaires pour créer l'article")
            @Valid ArticleCreation article) {
        return Response.ok(service.postOne(article, login)).build();
    }
}
