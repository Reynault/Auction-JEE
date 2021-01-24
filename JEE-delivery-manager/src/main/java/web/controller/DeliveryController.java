package web.controller;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.DeliveryServiceLocal;

@Path("/")
public class DeliveryController {

    @EJB
    private DeliveryServiceLocal service;

    @GET
    @Path("pending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingDeliveries() {
        return Response.ok(service.getPendingDeliveries()).build();
    }
}
