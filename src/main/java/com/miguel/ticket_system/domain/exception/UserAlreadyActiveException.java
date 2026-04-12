package com.miguel.ticket_system.domain.exception;

public class UserAlreadyActiveException extends RuntimeException {
    public UserAlreadyActiveException(Long id){
      super("User with id: " + id + " is already active");
    }

    public UserAlreadyActiveException(String message) {
        super(message);
    }
}
