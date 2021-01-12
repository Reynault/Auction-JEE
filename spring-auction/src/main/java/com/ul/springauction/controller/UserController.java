package com.ul.springauction.controller;

import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.auth.AuthResponse;
import com.ul.springauction.shared.auth.Login;
import com.ul.springauction.shared.auth.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String saveUser(@RequestBody Register register){
        /*
        ajout gestion avec le validator à ajouter
         */
        userService.save(register);
        return "Register complete";
    }

    @PostMapping(value = "/login")
    public AuthResponse loginUser(@RequestBody Login login) throws Exception {
        return userService.login(login);
    }
}
