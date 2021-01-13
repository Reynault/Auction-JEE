package service.auth;

import javax.ejb.Local;
import model.User;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;

@Local
public interface AuthServiceLocal {

    User authentificate(String login, String pass);

    Payload login(UserConnection connectionInfo);

    Payload register(UserInscription inscriptionInfo);
}
