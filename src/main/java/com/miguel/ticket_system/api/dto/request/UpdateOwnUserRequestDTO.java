package com.miguel.ticket_system.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateOwnUserRequestDTO(
        @Size(min = 4, max = 30) String username,
        @Size(max = 150) @Email String email
) {
}
