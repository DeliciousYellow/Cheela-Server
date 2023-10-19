package com.delicious.exception;

public class CallServiceException extends RuntimeException{

    public CallServiceException() {
        super();
    }

    public CallServiceException(String message) {
        super(message);
    }

    public CallServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
