package com.miguel.ticket_system.domain.exception;

public class UserAlreadyDeactivatedException extends RuntimeException {
    public UserAlreadyDeactivatedException(Long id){
      super("User with id: " + id + " is already deactivated");
    }

    public UserAlreadyDeactivatedException(String message) {
        super(message);
    }
}
