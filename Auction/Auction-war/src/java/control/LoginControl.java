package control;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import session.LoginBeanLocal;

@Named("login")
@RequestScoped
public class LoginControl {

    @Inject
    private UrlManager url;

    @EJB
    private LoginBeanLocal loginBean;

    private String username, password;

    public LoginControl() {
        username = "";
        password = "";
    }

    public String login() {
        boolean valid = loginBean.connectionValid(username, password);

        if (valid) {
            return url.home();
        } else {
            return "";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
