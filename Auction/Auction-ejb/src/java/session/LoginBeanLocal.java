package session;

import javax.ejb.Local;

@Local
public interface LoginBeanLocal {

    boolean connectionValid(String username, String password);
}
