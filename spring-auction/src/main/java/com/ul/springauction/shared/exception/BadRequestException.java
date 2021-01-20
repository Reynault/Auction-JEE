package com.ul.springauction.shared.exception;

/**
 * Simple custom exception for returning an error 400 Bad Request
 */
public class BadRequestException extends Exception{

    public BadRequestException(String err){
        super(err);
    }
}
