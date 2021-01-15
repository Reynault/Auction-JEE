package com.ul.springauction.controller;

import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.auth.Login;
import com.ul.springauction.shared.auth.RegisterUser;
import com.ul.springauction.shared.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auction/register")
    public Response saveUser(@RequestBody RegisterUser register){
        return userService.save(register);

    }

    @PostMapping(value = "/auction/login")
    public Response loginUser(@RequestBody Login login) {
        return userService.login(login);
    }
}
