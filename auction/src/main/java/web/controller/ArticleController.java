package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.article.ArticleServiceLocal;
import shared.dto.ArticleCreation;
import shared.dto.AuctionCreation;
import web.config.authentification.Secured;

/**
 * Controleur utilise pour recuperer et gerer les requetes qui concernent les
 * articles
 */
@Path("/articles")
public class ArticleController {

    @EJB
    private ArticleServiceLocal service;

    @POST
    @Secured
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitArticle(
            @HeaderParam("login") String login,
            @NotNull(message = "Veuillez fournir les informations nécéssaires pour créer l'article")
            @Valid ArticleCreation article) {
        return Response.ok(service.postOne(article, login)).build();
    }

    @POST
    @Secured
    @Path("{id}/sell")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellArticle(
            @PathParam("id") long id,
            @HeaderParam("login") String login,
            @NotNull(message = "Veuillez fournir les informations nécéssaires pour créer l'enchère")
            @Valid AuctionCreation auction) {
        return Response.ok(service.sellOne(auction, login, id)).build();
    }

//    @GET
//    public Response getAll(@BeanParam SearchParams search) {
//        return Response.ok(service.getAll(search)).build();
//    }
}
