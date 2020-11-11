package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LoginBean implements LoginBeanLocal {

    @PersistenceContext(name = "AuctionPU")
    private EntityManager em;

    @Override
    public boolean connectionValid(String username, String password) {
        String queryString = "SELECT u FROM User u "
                + "WHERE u.username = :username "
                + "AND u.password = :password";

        Query query = em.createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
