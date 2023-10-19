package com.delicious.exception;

import lombok.Data;

@Data
public class LoginFailureException extends RuntimeException {

    public LoginFailureException(String message) {
        super(message);
    }

    public LoginFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}