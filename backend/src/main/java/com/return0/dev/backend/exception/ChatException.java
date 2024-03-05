package com.return0.dev.backend.exception;

public class ChatException  extends BaseException{

    public ChatException(String code){
        super("chat." + code);
    }

    public static ChatException accessDenied(){
        return new ChatException("access.denied.not.found");
    }


}
