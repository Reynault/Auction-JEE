package dao.auth;

import javax.ejb.Local;
import model.User;
import shared.dto.UserAddress;
import shared.dto.UserInscription;

@Local
public interface UserDAOLocal {

    User getOne(String login);

    User insertOne(UserInscription info);

    UserAddress getAddress(String login, UserAddress address);
}
