package com.ul.springauction.controller;

import com.ul.springauction.configuration.Authentificated;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.dto.Login;
import com.ul.springauction.shared.dto.RegisterUser;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.Response;
import model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auction")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public Response saveUser(@RequestBody RegisterUser register) throws BadRequestException {
        return userService.save(register);
    }

    @PostMapping(value = "/login")
    public Response loginUser(@RequestBody Login login) throws BadRequestException {
        return userService.login(login);
    }

    @GetMapping(value = "/fetchAddress")
    @Authentificated
    public Address getUserAddress(@RequestHeader(value = "Authorization") String token) throws BadRequestException {
        return userService.getUserAddress(token);
    }
}
