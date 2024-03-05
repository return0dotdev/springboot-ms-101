package com.return0.dev.backend.exception;

public class FileException extends BaseException{

    public FileException(String code){
        super("user." + code);
    }

    public static FileException fileNull(){
        return new FileException("null");
    }

    public static FileException fileMaxSize(){
        return new FileException("max.size");
    }

    public static FileException unsupported(){
        return new FileException("max.unsupported.file.type.size");
    }
}
