package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
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
import shared.dto.AuctionCreation;
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

    @POST
    @Secured
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(
            @HeaderParam("login") String login,
            @NotNull(message = "Veuillez fournir les informations nécéssaires pour créer l'article")
            @Valid ArticleCreation article) {
        return Response.ok(service.postOne(article, login)).build();
    }

    @POST
    @Secured
    @Path("{id}/sell")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sell(
            @PathParam("id") long id,
            @HeaderParam("login") String login,
            @NotNull(message = "Veuillez fournir les informations nécéssaires pour créer l'enchère")
            @Valid AuctionCreation auction) {
        return Response.ok(service.sellOne(auction, login, id)).build();
    }

    @DELETE
    @Secured
    @Path("{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @PathParam("id") long id,
            @HeaderParam("login") String login) {
        service.delete(id, login);
        return Response.noContent().build();
    }

    @POST
    @Secured
    @Path("{id}/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(
            @PathParam("id") long id,
            @HeaderParam("login") String login) {
        service.remove(id, login);
        return Response.noContent().build();
    }

    @GET
    @Secured
    @Path("my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMine(@HeaderParam("login") String login) {
        return Response.ok(service.getMine(login)).build();
    }

    @GET
    @Secured
    @Path("{id}/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneOfMine(
            @PathParam("id") long id,
            @HeaderParam("login") String login) {
        return Response.ok(service.getOneOfMine(id, login)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@BeanParam SearchParams search) {
        return Response.ok(service.getAll(search)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@BeanParam SearchParams search) {
        return Response.ok(service.getAll(search)).build();
    }
}
