package com.vr.miniauthorizer.exception;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(final String message) {
        super(message);
    }
}