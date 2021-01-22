package com.ul.springauction.controller.exception;

import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour la gestion automatique des exceptions renvoyer par les autres controllers de l'application
 */
@RestControllerAdvice
@CrossOrigin(origins = "http://localhost:5201")
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleBadRequest(BadRequestException e){
        return new ErrorResponse(e.getMessage());
    }


}
