package com.ul.springauction.services.user;

import com.ul.springauction.DAO.UserRepository;
import com.ul.springauction.services.DtoValidator;
import com.ul.springauction.services.auth.JwtUtil;
import com.ul.springauction.shared.dto.RegisterAddress;
import com.ul.springauction.shared.exception.BadRequestException;
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

import java.util.ArrayList;

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


    public Response save(RegisterUser register) throws BadRequestException {
        dtoValidator.validation(register);
        User find = userRepo.findByLogin(register.getLogin());
        if (find == null){
            User u = new User(register.getLogin(), register.getPass(), register.getName(), register.getLastname(), RegisterAddress.convertToAddress(register.getAddress().orElse(null)), new ArrayList<>(), new ArrayList<>());
            u.setPass(bCryptPasswordEncoder.encode(u.getPass()));
            userRepo.save(u);
            UserDetails userDetails = userDetailService.loadUserByUsername(u.getLogin());
            String jwt = util.generateToken(userDetails);
            return new TokenResponse(jwt);
        } else {
            throw new BadRequestException("L'utilisateur est deja present");
        }
    }


    public Response login(Login login) throws BadRequestException {
        dtoValidator.validation(login);
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
        } catch (BadCredentialsException e){
            throw new BadRequestException("Le login ou le mot de passe est incorrect");
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(login.getLogin());
        String jwt = util.generateToken(userDetails);
        return new TokenResponse(jwt);
    }

    public User findUser(String token){
        token = token.substring(7);
        String username = util.extractUsername(token);
        return userRepo.findByLogin(username);
    }

    public void saveUpdatedUser(User u){
        userRepo.save(u);
    }
}
