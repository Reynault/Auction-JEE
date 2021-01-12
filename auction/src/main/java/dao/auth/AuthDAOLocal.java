package dao.auth;

import java.util.Collection;
import javax.ejb.Local;
import model.User;
import shared.dto.UserInscription;

@Local
public interface AuthDAOLocal {

    Collection<User> getOne(String login);

    User insertOne(UserInscription info);
}
