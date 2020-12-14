package service.auth;

import shared.dto.UserConnection;
import shared.dto.UserInscription;
import shared.entities.Payload;

import javax.ejb.Local;

@Local
public interface AuthServiceLocal {
    Payload login(UserConnection connectionInfo);
    Payload register(UserInscription inscriptionInfo);
}
