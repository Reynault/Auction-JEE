package web.config;

import web.controller.article.ArticleController;
import web.controller.auth.AuthController;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(web.config.ValidationExceptionMapper.class);
        resources.add(web.controller.article.ArticleController.class);
        resources.add(web.controller.auth.AuthController.class);
    }

}
