package com.vr.miniauthorizer.exception;

public class CardException extends RuntimeException {
    public CardException(String message) {
        super(message);
    }

    public CardException() {
        super();
    }

    public static class CardNotFoundException extends CardException {
        public CardNotFoundException(String message) {
            super(message);
        }
    }

    public static class CardAlreadyExistsException extends CardException {
        public CardAlreadyExistsException() {
            super();
        }
    }


}
