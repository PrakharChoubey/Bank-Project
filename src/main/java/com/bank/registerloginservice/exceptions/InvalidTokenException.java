package com.bank.registerloginservice.exceptions;

public class InvalidTokenException extends Exception{
    public  InvalidTokenException(String msg){
        super(msg);
    }
}
