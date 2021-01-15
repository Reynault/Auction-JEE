package com.ul.springauction.services.user;

import com.ul.springauction.DAO.UserRepository;
import com.ul.springauction.services.DtoValidator;
import com.ul.springauction.services.auth.JwtUtil;
import com.ul.springauction.shared.dto.RegisterAddress;
import com.ul.springauction.shared.response.ErrorResponse;
import com.ul.springauction.shared.response.TokenResponse;
import com.ul.springauction.shared.dto.Login;
import com.ul.springauction.shared.dto.RegisterUser;
import com.ul.springauction.shared.response.Response;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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


    public Response save(RegisterUser register){
        ErrorResponse errors = dtoValidator.validate(register);
        if (errors.getMessages().size() > 0){
            return errors;
        } else {
            User find = userRepo.findByLogin(register.getLogin());
            if (find == null){
                User u = new User(register.getLogin(), register.getPass(), register.getName(), register.getLastname(), RegisterAddress.convertToAddress(register.getAddress().orElse(null)), null, null, null);
                u.setPass(bCryptPasswordEncoder.encode(u.getPass()));
                userRepo.save(u);
                UserDetails userDetails = userDetailService.loadUserByUsername(u.getLogin());
                String jwt = util.generateToken(userDetails);
                return new TokenResponse(jwt);
            } else {
                return new ErrorResponse("L'utilisateur existe deja");
            }
        }
    }


    public Response login(Login login) {
        ErrorResponse errors = dtoValidator.validate(login);
        if (errors.getMessages().size() > 0) {
            return errors;
        } else {
            try{
                authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
            } catch (BadCredentialsException e){
                return new ErrorResponse("Le login ou le mot de passe n'est pas bon");
            }
            UserDetails userDetails = userDetailService.loadUserByUsername(login.getLogin());
            String jwt = util.generateToken(userDetails);
            return new TokenResponse(jwt);
        }
    }
}
