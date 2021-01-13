package service.jwt;

import javax.ejb.Local;

@Local
public interface JWTServiceLocal {

    String generateToken(String login);

    Boolean validateToken(String token, String login);
}
