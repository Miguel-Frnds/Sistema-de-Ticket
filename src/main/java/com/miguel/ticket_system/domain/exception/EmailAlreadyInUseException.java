package com.miguel.ticket_system.domain.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("Email is already in use");
    }

    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
