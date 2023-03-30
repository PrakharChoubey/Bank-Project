package com.bank.registerloginservice.exceptions;


public class IncorrectCredentialsException extends Exception{
    public IncorrectCredentialsException(String msg){
        super(msg);
    }
}
