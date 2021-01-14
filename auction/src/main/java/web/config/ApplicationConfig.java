package web.config;

import web.config.exceptionMapper.JSONParsingMapper;
import web.config.exceptionMapper.JSONBExceptionMapper;
import web.config.exceptionMapper.ConstraintMapper;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        resources.add(JSONBExceptionMapper.class);
        resources.add(ConstraintMapper.class);
        resources.add(JSONParsingMapper.class);

        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(web.config.authentification.AuthentificationFilter.class);
        resources.add(web.controller.ArticleController.class);
        resources.add(web.controller.AuthController.class);
    }

}
