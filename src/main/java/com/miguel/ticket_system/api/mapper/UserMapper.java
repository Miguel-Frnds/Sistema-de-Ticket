package com.miguel.ticket_system.api.mapper;

import com.miguel.ticket_system.api.dto.request.AdminCreateUserRequestDTO;
import com.miguel.ticket_system.api.dto.request.ClientRegisterRequestDTO;
import com.miguel.ticket_system.api.dto.response.UserResponseDTO;
import com.miguel.ticket_system.domain.model.Role;
import com.miguel.ticket_system.domain.model.User;

public class UserMapper {
    public static User toEntity(ClientRegisterRequestDTO dto){
        User user = new User();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(Role.CLIENT);
        user.setActive(true);

        return user;
    }
    
    public static User toEntity(AdminCreateUserRequestDTO dto){
        User user = new User();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setActive(true);

        return user;
    }

    public static UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
