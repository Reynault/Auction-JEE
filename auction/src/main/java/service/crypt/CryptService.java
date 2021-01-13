package service.crypt;

import javax.ejb.Stateless;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class CryptService implements CryptServiceLocal {

    private final static int rounds = 12;

    public String hash(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public boolean verif(String encryptedPass, String passToVerify) {
        return BCrypt.checkpw(passToVerify, encryptedPass);
    }
}
