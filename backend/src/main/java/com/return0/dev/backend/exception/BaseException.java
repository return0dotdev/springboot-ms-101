package com.return0.dev.backend.exception;

public abstract class BaseException extends Exception {

    public BaseException(String code){
        super(code);
    }
}
