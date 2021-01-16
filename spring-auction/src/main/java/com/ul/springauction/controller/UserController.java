package com.ul.springauction.controller;

import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.dto.Login;
import com.ul.springauction.shared.dto.RegisterUser;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import com.ul.springauction.shared.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auction/register")
    public Response saveUser(@RequestBody RegisterUser register) throws BadRequestException {
        return userService.save(register);

    }

    @PostMapping(value = "/auction/login")
    public Response loginUser(@RequestBody Login login) throws BadRequestException {
        return userService.login(login);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleBadRequest(BadRequestException e){
        return new ErrorResponse(e.getMessage());
    }
}
