package com.delicious.exception;

import lombok.Data;

@Data
public class ErrorException extends Exception {
    private Exception exception;

    public ErrorException(Exception e) {
        this.exception = e;
    }

}