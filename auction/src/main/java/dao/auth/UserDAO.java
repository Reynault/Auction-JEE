package dao.auth;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Address;
import model.User;
import shared.dto.UserAddress;
import shared.dto.UserInscription;

@Stateless
public class UserDAO implements UserDAOLocal {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Override
    public User getOne(String login) {
        Query query = em.createNamedQuery("User.findOne", User.class);
        query.setParameter("login", login);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User insertOne(UserInscription info) {
        User user = new User(
                info.getLogin(),
                info.getPass(),
                info.getName(),
                info.getLastname(),
                UserAddress.convertToAddress(info.getAddress().orElse(null)),
                new ArrayList(),
                new ArrayList()
        );
        em.persist(user);
        return user;
    }

    @Override
    public UserAddress getAddress(String login, UserAddress address) {
        if (address == null) {
            Query q = em.createNamedQuery("User.getAddress");
            q.setParameter("login", login);
            try {
                Address a = (Address) q.getSingleResult();
                if (a != null) {
                    return new UserAddress(
                            a.getCountry(), a.getCity(), a.getStreet(), a.getCode()
                    );
                } else {
                    return null;
                }
            } catch (NoResultException e) {
                return null;
            }
        } else {
            return address;
        }
    }

}
