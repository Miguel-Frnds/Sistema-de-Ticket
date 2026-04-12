package com.miguel.ticket_system.domain.service;

import com.miguel.ticket_system.api.dto.request.AdminCreateUserRequestDTO;
import com.miguel.ticket_system.api.dto.request.ClientRegisterRequestDTO;
import com.miguel.ticket_system.api.dto.request.UpdateOwnUserRequestDTO;
import com.miguel.ticket_system.api.dto.response.UserResponseDTO;
import com.miguel.ticket_system.api.mapper.UserMapper;
import com.miguel.ticket_system.domain.exception.*;
import com.miguel.ticket_system.domain.model.Role;
import com.miguel.ticket_system.domain.model.User;
import com.miguel.ticket_system.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAllUsers(){
        // TODO: Verificar se quem faz a requisição é um ADMIN autenticado
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    // TODO: Verificar se o usuário autenticado corresponde ao id informado
    public UserResponseDTO getOwnProfile(Long id){
        return UserMapper.toResponseDTO(findUserById(id));
    }

    // TODO: Verificar se quem faz a requisição é um ADMIN autenticado
    public UserResponseDTO getUserById(Long id){
        return UserMapper.toResponseDTO(findUserById(id));
    }

    // TODO: Cadastro público para CLIENT
    // TODO: Aplicar validações de unicidade e criptografar senha
    public UserResponseDTO registerClient(ClientRegisterRequestDTO dto){
        validateUsername(dto.username());
        validateEmail(dto.email());

        User user = UserMapper.toEntity(dto);

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    // TODO: Se a role for ADMIN ou TECHNICIAN, validar se o usuário autenticado é ADMIN
    // TODO: Aplicar validações de unicidade e criptografar senha
    public UserResponseDTO createUserByAdmin(AdminCreateUserRequestDTO dto){
        validateUsername(dto.username());
        validateEmail(dto.email());

        User user = UserMapper.toEntity(dto);

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO updateOwnUser(Long id, UpdateOwnUserRequestDTO dto){
        // TODO: Verificar se quem faz a requisição é o próprio User autenticado
        User foundUser = findUserById(id);

        validateUsername(dto.username(), id);
        validateEmail(dto.email(), id);

        if(dto.username() != null && !dto.username().isBlank()) {
            foundUser.setUsername(dto.username());
        }

        if(dto.email() != null && !dto.email().isBlank()) {
            foundUser.setEmail(dto.email());
        }

        return UserMapper.toResponseDTO(userRepository.save(foundUser));
    }

    public UserResponseDTO changeUserRole(Long id, Role role){
        // TODO: Verificar se quem faz a alteração é um User ADMIN autenticado
        // TODO: Verificar se o usuário autenticado está tentando alterar a própria role
        User foundUser = findUserById(id);

        if(foundUser.getRole().equals(role)) {
            throw new UserAlreadyHasRoleException(id, role);
        }

        foundUser.setRole(role);
        return UserMapper.toResponseDTO(userRepository.save(foundUser));
    }

    public UserResponseDTO activateUser(Long id){
        // TODO: Verificar se quem faz a alteração é um User ADMIN autenticado
        User foundUser = findUserById(id);

        if(foundUser.isActive()) {
            throw new UserAlreadyActiveException(id);
        }

        foundUser.setActive(true);
        return UserMapper.toResponseDTO(userRepository.save(foundUser));
    }

    public UserResponseDTO deactivateUser(Long id){
        // TODO: Verificar se quem faz a alteração é um User ADMIN autenticado
        // TODO: Verificar se o usuário autenticado está tentando desativar a própria conta
        User foundUser = findUserById(id);

        if(!foundUser.isActive()) {
            throw new UserAlreadyDeactivatedException(id);
        }

        foundUser.setActive(false);
        return UserMapper.toResponseDTO(userRepository.save(foundUser));
    }

    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateUsername(String username){
        if(username != null && !username.isBlank()) {
            if(userRepository.existsByUsername(username)){
                throw new UsernameAlreadyInUseException();
            }
        }
    }

    private void validateEmail(String email){
        if(email != null && !email.isBlank()) {
            if(userRepository.existsByEmail(email)){
                throw new EmailAlreadyInUseException();
            }
        }
    }

    private void validateUsername(String username, Long id){
        if(username != null && !username.isBlank()) {
            if(userRepository.existsByUsernameAndIdNot(username, id)){
                throw new UsernameAlreadyInUseException();
            }
        }
    }

    private void validateEmail(String email, Long id){
        if(email != null && !email.isBlank()) {
            if(userRepository.existsByEmailAndIdNot(email, id)){
                throw new EmailAlreadyInUseException();
            }
        }
    }
}
