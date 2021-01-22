package dao.user;

import javax.ejb.Local;
import model.Address;
import model.User;
import shared.dto.UserInscription;

@Local
public interface UserDAOLocal {

    User getOne(String login);

    User insertOne(UserInscription info);

    User changeAddress(User user, Address address);
}
