package web.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.delivery.DeliveryServiceLocal;
import service.offer.OfferServiceLocal;
import service.participation.ParticipateServiceLocal;
import shared.dto.UserAddress;
import shared.dto.UserParticipate;
import shared.params.PromoParams;
import web.config.authentification.Secured;

@Path("/participation")
public class ParticipationController {

    @EJB
    private ParticipateServiceLocal service;
    @EJB
    private DeliveryServiceLocal delivery;

    @EJB
    private OfferServiceLocal offer;

    @GET
    @Secured
    @Path("my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyParticipatedArticles(@HeaderParam("login") String login) {
        return Response.ok(service.getMyParticipatedArticles(login)).build();
    }

    @GET
    @Path("promotions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPromotions() {
        return Response.ok(offer.getDailyPromotions()).build();
    }

    @GET
    @Secured
    @Path("{id}/checkPrice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPrice(
            @HeaderParam("login") String login,
            @PathParam("id") long id,
            @BeanParam PromoParams params) {
        return Response.ok(offer.checkPrice(login, id, params)).build();
    }

    @GET
    @Secured
    @Path("{id}/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneParticipatedArticle(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        return Response.ok(service.getOneParticipatedArticle(login, id)).build();
    }

    @POST
    @Secured
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response participate(
            @HeaderParam("login") String login,
            @PathParam("id") long id,
            @NotNull(message = "Veuillez fournir vos donn�es de participation")
            @Valid UserParticipate participation) {
        return Response.ok(service.participate(participation, login, id)).build();
    }

    @POST
    @Secured
    @Path("{id}/deliver")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deliver(
            @HeaderParam("login") String login,
            @PathParam("id") long id,
            @BeanParam PromoParams params,
            @Valid UserAddress address) {
        return Response.ok(delivery.deliver(address, params, login, id)).build();
    }

    @GET
    @Secured
    @Path("deliveries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveries(@HeaderParam("login") String login) {
        return Response.ok(delivery.getDeliveries(login)).build();
    }

    @GET
    @Secured
    @Path("{id}/deliveries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneDelivery(
            @HeaderParam("login") String login,
            @PathParam("id") long id) {
        return Response.ok(delivery.getOneDelivery(login, id)).build();
    }
}
