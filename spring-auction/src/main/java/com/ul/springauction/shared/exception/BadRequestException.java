package com.ul.springauction.shared.exception;

/**
 * Une exception simple permettant de notifier le controller
 * Lorsque déclanchée, le controller retournera une erreur 400
 */
public class BadRequestException extends Exception{

    public BadRequestException(String err){
        super(err);
    }
}
