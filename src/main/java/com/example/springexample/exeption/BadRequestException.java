package com.example.springexample.exeption;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String mess){
        super(mess);
    }
}
