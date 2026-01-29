package com.example.springexample.exeption;

public class CastomException extends RuntimeException{

    public CastomException(String mess){
        super(mess);
    }

    public CastomException(String resourceName, String fieldName, Object fieldValue){
        super(String.format(resourceName,fieldName,fieldValue));
    }
}
