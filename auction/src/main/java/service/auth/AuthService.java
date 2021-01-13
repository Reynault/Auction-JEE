package service.auth;

import dao.auth.AuthDAOLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.User;
import service.crypt.CryptServiceLocal;
import service.jwt.JWTServiceLocal;
import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;
import web.exceptions.BadValuesException;

@Stateless
public class AuthService implements AuthServiceLocal {

    @EJB
    private AuthDAOLocal dao;

    @EJB
    private CryptServiceLocal crypt;

    @EJB
    private JWTServiceLocal jwt;

    /**
     * @return null si impossible d'authentifier l'utilisateur, sinon
     * l'utilisateur
     */
    @Override
    public User authentificate(String login, String pass) {
        List<User> users = (List<User>) dao.getOne(login);
        if (users.isEmpty()) { // verify if user exists
            throw new BadValuesException("Le login n'existe pas");
        } else { // verify pass with given one
            User u = users.get(0);
            if (crypt.verif(u.getPass(), pass)) {
                return u;
            } else {
                return null;
            }
        }
    }

    @Override
    public Payload login(UserConnection connectionInfo) {
        if (authentificate(connectionInfo.getLogin(), connectionInfo.getPass()) != null) {
            return new Payload(jwt.generateToken(connectionInfo.getLogin()));
        } else {
            throw new BadValuesException("Les données fournies ne nous ont pas permis de vous identifier");
        }
    }

    @Override
    public Payload register(UserInscription inscriptionInfo) {
        if (dao.getOne(inscriptionInfo.getLogin()).isEmpty()) {
            // get encrypted password
            inscriptionInfo.setPass(crypt.hash(inscriptionInfo.getPass()));
            // insert user
            User user = dao.insertOne(inscriptionInfo);
            // generate payload
            return new Payload(jwt.generateToken(user.getLogin()));
        } else {
            throw new BadValuesException("Le login existe déjà");
        }
    }
}
