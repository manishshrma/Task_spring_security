package com.eminence.innovation.task.exceptionHandler;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
    public UserAlreadyRegisteredException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
