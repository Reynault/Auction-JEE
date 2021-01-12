package com.ul.springauction.services.user;

import com.ul.springauction.DAO.UserRepository;
import com.ul.springauction.model.User;
import com.ul.springauction.services.auth.JwtUtil;
import com.ul.springauction.shared.auth.AuthResponse;
import com.ul.springauction.shared.auth.Login;
import com.ul.springauction.shared.auth.Register;
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



    public void save(Register register){
        User u = new User(register.getLogin(), register.getPass(), register.getName(), register.getLastname());
        u.setPass(bCryptPasswordEncoder.encode(u.getPass()));
        userRepo.save(u);
    }


    public AuthResponse login(Login login) throws Exception {
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username ou mot de passe", e);
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(login.getLogin());
        String jwt = util.generateToken(userDetails);
        return new AuthResponse(jwt);
    }
}
