package com.vr.miniauthorizer.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(final String message) {
        super(message);
    }
}