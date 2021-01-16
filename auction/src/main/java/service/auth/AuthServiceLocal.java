package service.auth;

import javax.ejb.Local;
import model.User;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Entity;

@Local
public interface AuthServiceLocal {

    User authentificate(String login, String pass);

    Entity login(UserConnection connectionInfo);

    Entity register(UserInscription inscriptionInfo);
}
