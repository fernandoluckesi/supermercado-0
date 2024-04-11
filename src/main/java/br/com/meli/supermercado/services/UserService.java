package br.com.meli.supermercado.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.meli.supermercado.dtos.UserDTO;
import br.com.meli.supermercado.dtos.UserResponse;
import br.com.meli.supermercado.entities.UserEntity;
import br.com.meli.supermercado.exceptions.ValidationExceptionConflict;
import br.com.meli.supermercado.exceptions.ValidationExceptionNotFound;
import br.com.meli.supermercado.exceptions.ValidationExceptionBadRequest;
import br.com.meli.supermercado.repositories.UserRepository;
import br.com.meli.supermercado.utils.Validations;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Validations validations;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserEntity create(UserDTO userDTO) {

    this.userRepository.findByEmail(userDTO.getEmail())
        .ifPresent((user) -> {
          throw new ValidationExceptionConflict(HttpStatus.CONFLICT.name(), "Usuário já cadastrado com esse e-mail",
              HttpStatus.CONFLICT.value());
        });

    this.userRepository.findByCpf(userDTO.getCpf())
        .ifPresent((user) -> {
          throw new ValidationExceptionConflict(HttpStatus.CONFLICT.name(), "Usuário já cadastrado com esse cpf",
              HttpStatus.CONFLICT.value());
        });

    Boolean under18YearsOld = this.validations.under18YearsOld(userDTO.getDateOfBirth());
    if (under18YearsOld) {
      throw new ValidationExceptionBadRequest(HttpStatus.BAD_REQUEST.name(), "Usuário deve ter 18 anos ou mais",
          HttpStatus.BAD_REQUEST.value());
    }

    UserEntity user = new UserEntity(userDTO);

    var password = passwordEncoder.encode(userDTO.getPassword());
    user.setPassword(password);

    return this.userRepository.save(user);
  }

  public UserEntity update(UUID id, UserDTO userDTO) {

    UserEntity user = this.userRepository.findById(id)
        .orElseThrow(() -> new ValidationExceptionNotFound(HttpStatus.NOT_FOUND.name(), "Usuário não encontrado",
            HttpStatus.NOT_FOUND.value()));

    Boolean under18YearsOld = this.validations.under18YearsOld(userDTO.getDateOfBirth());
    if (under18YearsOld) {
      throw new ValidationExceptionBadRequest(HttpStatus.BAD_REQUEST.name(), "Usuário deve ter 18 anos ou mais",
          HttpStatus.BAD_REQUEST.value());
    }

    UserEntity updateUser = new UserEntity(userDTO);

    updateUser.setId(user.getId());
    updateUser.setCreatedAt(user.getCreatedAt());

    return userRepository.save(updateUser);
  }

  public UserEntity delete(UUID id) {

    UserEntity user = this.userRepository.findById(id)
        .orElseThrow(() -> new ValidationExceptionNotFound(HttpStatus.NOT_FOUND.name(), "Usuário não encontrado",
            HttpStatus.NOT_FOUND.value()));

    userRepository.delete(user);

    return user;
  }

  public UserResponse getAll(String name) {

    List<UserEntity> users;

    if (name != null && !name.isEmpty()) {
      users = this.userRepository.findByCompleteNameLikeIgnoreCase("%" + name + "%");
    } else {

      users = this.userRepository.findAll();
    }

    return new UserResponse(LocalDateTime.now(), users, users.size(), null);
  }

  public UserResponse getById(UUID id) {

    UserEntity user = this.userRepository.findById(id)
        .orElseThrow(() -> new ValidationExceptionNotFound(HttpStatus.NOT_FOUND.name(), "Usuário não encontrado",
            HttpStatus.NOT_FOUND.value()));

    return new UserResponse(LocalDateTime.now(), user, null);
  }

}