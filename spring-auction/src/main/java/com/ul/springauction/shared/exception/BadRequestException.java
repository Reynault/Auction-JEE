package com.ul.springauction.shared.exception;

public class BadRequestException extends Exception{

    public BadRequestException(String err){
        super(err);
    }
}
