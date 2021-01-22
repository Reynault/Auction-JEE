package com.ul.springauction.services.user;

import com.ul.springauction.DAO.repository.UserRepository;
import com.ul.springauction.services.validator.DtoValidator;
import com.ul.springauction.services.auth.JwtUtil;
import com.ul.springauction.shared.dto.RegisterAddress;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.TokenResponse;
import com.ul.springauction.shared.dto.Login;
import com.ul.springauction.shared.dto.RegisterUser;
import com.ul.springauction.shared.response.Response;
import model.Address;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shared.ErrorMessageManager;

import java.util.ArrayList;

/**
 * Service de gestion des utilisateurs
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtil util;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DtoValidator dtoValidator;


    // Enregistre un nouvel utilisateur non existant à la base de données
    public Response save(RegisterUser register) throws BadRequestException {
        dtoValidator.validation(register);
        User find = userRepo.findByLogin(register.getLogin());
        // Si l'utilisateur n'existe pas, on peut créer le compte
        if (find == null){
            User u = new User(register.getLogin(), register.getPass(), register.getName(), register.getLastname(), RegisterAddress.convertToAddress(register.getAddress().orElse(null)), new ArrayList<>(), new ArrayList<>());
            u.setPass(bCryptPasswordEncoder.encode(u.getPass())); // On crypt son mot de passe
            userRepo.save(u);
            // Création du JWT
            UserDetails userDetails = userDetailService.loadUserByUsername(u.getLogin());
            String jwt = util.generateToken(userDetails);
            return new TokenResponse(jwt);
        } else {
            throw new BadRequestException(ErrorMessageManager.USER_ALREADY_EXIST);
        }
    }


    // Connecte un utilisateur en vérifiant son login/mdp et renvoyant son JWT
    public Response login(Login login) throws BadRequestException {
        dtoValidator.validation(login);
        // On essaye la combinaison login/mdp
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
        } catch (BadCredentialsException e){
            throw new BadRequestException(ErrorMessageManager.COULDNT_AUTHENTIFY);
        }
        // Création du JWT si elle est bonne
        UserDetails userDetails = userDetailService.loadUserByUsername(login.getLogin());
        String jwt = util.generateToken(userDetails);
        return new TokenResponse(jwt);
    }


    // Extraction de l'utilisateur en déchiffrant le JWT
    public User findUser(String token){
        // Retrait des 7 premiers caractères de la chaine
        // Car JWT de la forme "Bearer suitedecaractèresindéchiffrables"
        token = token.substring(7);
        String username = util.extractUsername(token);
        return userRepo.findByLogin(username);
    }


    // Donne l'addresse de l'utilisateur
    public Address getUserAddress(String token) throws BadRequestException {
        User u = findUser(token);
        if (u.getHome() == null){
            throw new BadRequestException(ErrorMessageManager.USER_DOESNT_HAVE_ADDRESS);
        } else {
            return u.getHome();
        }
    }


    // Sauvegarde un utilisateur qui à modifier grâce à son repository
    public void saveUpdatedUser(User u){
        userRepo.save(u);
    }
}
