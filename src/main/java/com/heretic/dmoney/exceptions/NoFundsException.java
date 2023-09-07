package com.heretic.dmoney.exceptions;

public class NoFundsException extends RuntimeException {

    public NoFundsException(String message) {
        super(message);
    }
}