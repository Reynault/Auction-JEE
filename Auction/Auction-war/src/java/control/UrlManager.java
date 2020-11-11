package control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("url")
@RequestScoped
public class UrlManager {
    public String home(){
        return "index";
    }

    public String login(){
        return "login";
    }
}
