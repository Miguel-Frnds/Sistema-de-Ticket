package com.miguel.ticket_system.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRegisterRequestDTO(
        @NotBlank @Size(min = 4, max = 30) String username,
        @NotBlank @Size(max = 150) @Email String email,
        @NotBlank @Size(min = 8, max = 100) String password
) {
}
