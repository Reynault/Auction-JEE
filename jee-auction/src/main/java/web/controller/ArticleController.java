package web.controller;

import model.Article;
import service.ArticleServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Path("/articles")
public class ArticleController implements ArticleControllerLocal{

    @EJB
    private ArticleServiceLocal service;

    @Override
    @GET
    public Response getAll() {
        Collection<Article> articles = service.getAll();
        return Response.ok("Hello World !").build();
    }
}
