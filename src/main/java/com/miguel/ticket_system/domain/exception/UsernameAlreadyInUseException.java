package com.miguel.ticket_system.domain.exception;

public class UsernameAlreadyInUseException extends RuntimeException {
  public UsernameAlreadyInUseException(){
    super("Username is already in use");
  }
  public UsernameAlreadyInUseException(String message) {
    super(message);
  }
}
