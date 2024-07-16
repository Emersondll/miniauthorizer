package com.vr.miniauthorizer.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(final String message) {
        super(message);
    }
}