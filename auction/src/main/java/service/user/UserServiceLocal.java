package service.user;

import javax.ejb.Local;
import model.Address;
import model.User;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Entity;

@Local
public interface UserServiceLocal {

    User authentificate(String login, String pass);

    Entity login(UserConnection connectionInfo);

    Entity register(UserInscription inscriptionInfo);

    Address getAddress(String login);
}
