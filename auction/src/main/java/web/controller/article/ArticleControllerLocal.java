package web.controller.article;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface ArticleControllerLocal {
    Response getAll();
}
