package com.miguel.ticket_system.domain.exception;

import com.miguel.ticket_system.domain.model.Role;

public class UserAlreadyHasRoleException extends RuntimeException {
    public UserAlreadyHasRoleException(Long id, Role role) {
      super("User with id:" + id + " already has role: " + role.name());
    }

    public UserAlreadyHasRoleException(String message) {
        super(message);
    }
}
