package dao.auth;

import javax.ejb.Local;
import model.User;
import shared.dto.UserInscription;

@Local
public interface UserDAOLocal {

    User getOne(String login);

    User insertOne(UserInscription info);
}
