package web.config;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import web.config.exceptionMapper.ConstraintMapper;
import web.config.exceptionMapper.JSONBExceptionMapper;
import web.config.exceptionMapper.JSONParsingMapper;

@ApplicationPath("/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        resources.add(JSONBExceptionMapper.class);
        resources.add(ConstraintMapper.class);
        resources.add(JSONParsingMapper.class);
        resources.add(JacksonFeature.class);

        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(web.config.CorsFilter.class);
        resources.add(web.config.authentification.AuthentificationFilter.class);
        resources.add(web.controller.ArticleController.class);
        resources.add(web.controller.AuthController.class);
        resources.add(web.controller.ParticipationController.class);
    }

}
