package web.controller.auth;

import shared.dto.UserConnection;
import shared.dto.UserInscription;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface AuthControllerLocal {
    Response login(UserConnection userConnection);
    Response register(UserInscription userConnection);
}
