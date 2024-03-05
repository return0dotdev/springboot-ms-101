package com.return0.dev.backend.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException unauthorized() {
        return new UserException("user.unauthorized");
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }


    // CREATE
    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    public static UserException createNameNull() {
        return new UserException("create.name.null");
    }

    public static UserException createEmailDuplicate() {
        return new UserException("create.email.duplicate");
    }

    // LOGIN
    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }
    public static UserException loginFailUnactivated() {
        return new UserException("login.fail.unactivated");
    }

    // ACTIVATE
    public static UserException activateTokenisNull() {
        return new UserException("actiate.token.null");
    }

    public static UserException activateTokenFailed() {
        return new UserException("actiate.token.failed");
    }

    public static UserException activateTokenExp() {
        return new UserException("actiate.token.expired");
    }

    // RESEND EMAIL ACTIVATE
    public static UserException resendActTokenNotMatch() {
        return new UserException("actiate.token.not.match");
    }

    // UPDATE
    public static UserException nameNull() {
        return new UserException("update.name.null");
    }




}
