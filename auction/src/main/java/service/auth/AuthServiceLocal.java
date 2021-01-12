package service.auth;

import javax.ejb.Local;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;

@Local
public interface AuthServiceLocal {

    boolean authentificate(String login, String pass);

    Payload login(UserConnection connectionInfo);

    Payload register(UserInscription inscriptionInfo);
}
