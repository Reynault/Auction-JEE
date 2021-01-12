package service.auth;

import dao.auth.AuthDAOLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.User;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;
import web.exceptions.BadValuesException;

@Stateless
public class AuthService implements AuthServiceLocal {

    @EJB
    private AuthDAOLocal dao;

    @Override
    public boolean authentificate(String login, String pass) {
        List<User> users = (List<User>) dao.getOne(login);
        if (users.isEmpty()) {
            throw new BadValuesException("Le login n'existe pas");
        } else {
            User u = users.get(0);

        }
        return false;
    }

    @Override
    public Payload login(UserConnection connectionInfo) {
        if (authentificate(connectionInfo.getLogin(), connectionInfo.getPass())) {
            return new Payload(connectionInfo.getLogin(), "token", "exp time");
        } else {
            throw new BadValuesException("Les données fournies ne nous ont pas permis de vous identifier");
        }
    }

    @Override
    public Payload register(UserInscription inscriptionInfo) {
        if (dao.getOne(inscriptionInfo.getLogin()).isEmpty()) {
            return new Payload(dao.insertOne(inscriptionInfo).getLogin(), "token", "exp time");
        } else {
            throw new BadValuesException("Le login existe déjà");
        }
    }
}
