package service.crypt;

import javax.ejb.Local;

@Local
public interface CryptServiceLocal {

    String hash(String pass);

    boolean verif(String encryptedPass, String passToVerify);
}
