package web.controller;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface ArticleControllerLocal {
    Response getAll();
}
