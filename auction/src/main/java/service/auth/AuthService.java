package service.auth;

import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;

import javax.ejb.Stateless;

@Stateless
public class AuthService implements AuthServiceLocal{

    public String generateJWTToken(String login) {
        return null;
    }

    public boolean authentificate(String login, String pass) {
        return false;
    }

    @Override
    public Payload login(UserConnection connectionInfo) {
        return new Payload(connectionInfo.getLogin(), "token", "exp time");
    }

    @Override
    public Payload register(UserInscription inscriptionInfo) {
        return new Payload(inscriptionInfo.getLogin(), "token", "exp time");
    }
}
