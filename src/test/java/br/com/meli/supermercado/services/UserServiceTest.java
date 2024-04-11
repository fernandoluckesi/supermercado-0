package br.com.meli.supermercado.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.meli.supermercado.dtos.UserDTO;
import br.com.meli.supermercado.dtos.UserResponse;
import br.com.meli.supermercado.entities.UserEntity;
import br.com.meli.supermercado.exceptions.ValidationException;
import br.com.meli.supermercado.exceptions.ValidationExceptionBadRequest;
import br.com.meli.supermercado.exceptions.ValidationExceptionConflict;
import br.com.meli.supermercado.exceptions.ValidationExceptionNotFound;
import br.com.meli.supermercado.repositories.UserRepository;
import br.com.meli.supermercado.utils.Validations;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Autowired
  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private Validations validations;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should be able to create a new user")
  public void should_be_able_to_create_a_new_user() {

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    userService.create(data);

    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("Should throw validation exception when user is already registere with email")
  public void should_throw_validation_exception_when_user_is_already_registered_with_email() {

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    ValidationExceptionConflict exception = assertThrows(ValidationExceptionConflict.class, () -> {
      userService.create(data);
    });

    assertEquals("Usuário já cadastrado com esse e-mail", exception.getMessage());

    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should throw validation exception when user is already registere with cpf")
  public void should_throw_validation_exception_when_user_is_already_registered_with_cpf() {

    when(userRepository.findByCpf(anyString())).thenReturn(Optional.of(new UserEntity()));

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    ValidationException exception = assertThrows(ValidationExceptionConflict.class, () -> {
      userService.create(data);
    });

    assertEquals("Usuário já cadastrado com esse cpf", exception.getMessage());

    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should not be able to create a new user under 18 years old")
  public void should_not_be_able_to_create_a_new_user_under_18_years_old() {
    when(validations.under18YearsOld(LocalDate.of(2007, 12,
        13))).thenReturn(true);

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2007, 12, 13));

    ValidationExceptionBadRequest exception = assertThrows(ValidationExceptionBadRequest.class, () -> {
      userService.create(data);
    });

    String expectedMessage = "Usuário deve ter 18 anos ou mais";
    assertEquals(expectedMessage, exception.getMessage());

    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should be able to update a user")
  public void should_be_able_to_update_a_user() {

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2007, 12, 13));

    when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));

    userService.update(any(), data);

    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("Should not be able to update a user nonexistent ")
  public void should_not_be_able_to_update_a_user_insistent() {

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    when(userRepository.findById(any())).thenReturn(Optional.empty());

    ValidationExceptionNotFound exception = assertThrows(ValidationExceptionNotFound.class, () -> {
      userService.update(any(), data);
    });

    String expectedMessage = "Usuário não encontrado";
    assertEquals(expectedMessage, exception.getMessage());

    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should not be able to update a user under 18 years old")
  public void should_not_be_able_to_update_a_user_under_18_years_old() {

    UserEntity user = UserEntity.builder()
        .id(UUID.randomUUID())
        .completeName("Fernando")
        .password("1234567891011")
        .email("fernando@gmail.com")
        .cpf("42275937862")
        .dateOfBirth(LocalDate.of(2005, 12, 13))
        .createdAt(LocalDateTime.of(2023, 12, 14, 16, 12, 12))
        .updateAt(LocalDateTime.of(2023, 12, 14, 16, 12, 16)).build();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    when(validations.under18YearsOld(LocalDate.of(2007, 12,
        13))).thenReturn(true);

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2007, 12, 13));

    ValidationExceptionBadRequest exception = assertThrows(ValidationExceptionBadRequest.class, () -> {
      userService.update(any(), data);
    });

    String expectedMessage = "Usuário deve ter 18 anos ou mais";
    assertEquals(expectedMessage, exception.getMessage());

    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should be able to delete a user")
  public void should_be_able_to_delete_a_user() {

    UserEntity user = UserEntity.builder()
        .id(UUID.randomUUID())
        .completeName("Fernando")
        .password("1234567891011")
        .email("fernando@gmail.com")
        .cpf("42275937862")
        .dateOfBirth(LocalDate.of(2005, 12, 13))
        .createdAt(LocalDateTime.of(2023, 12, 14, 16, 12, 12))
        .updateAt(LocalDateTime.of(2023, 12, 14, 16, 12, 16)).build();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    userService.delete(any());

    verify(userRepository, times(1)).delete(any());
  }

  @Test
  @DisplayName("Should not be able to delete a user nonexistent ")
  public void should_not_be_able_to_delete_a_user_insistent() {

    when(userRepository.findById(any())).thenReturn(Optional.empty());

    ValidationExceptionNotFound exception = assertThrows(ValidationExceptionNotFound.class, () -> {
      userService.delete(any());
    });

    String expectedMessage = "Usuário não encontrado";
    assertEquals(expectedMessage, exception.getMessage());

    verify(userRepository, times(0)).delete(any());
  }

  @Test
  @DisplayName("Should get all users with and without a name")
  public void should_get_all_users_with_and_without_name() {

    String name = "Fernando";
    List<UserEntity> usersWithName = Arrays.asList(
        new UserEntity(),
        new UserEntity());
    when(userRepository.findByCompleteNameLikeIgnoreCase("%" + name + "%")).thenReturn(usersWithName);

    UserResponse responseWithName = userService.getAll(name);

    verify(userRepository, times(1)).findByCompleteNameLikeIgnoreCase("%" + name + "%");
    assertEquals(usersWithName.size(), responseWithName.getUsers().size());

    when(userRepository.findAll()).thenReturn(usersWithName);

    UserResponse responseWithoutName = userService.getAll(null);

    verify(userRepository, times(1)).findAll();
    assertEquals(usersWithName.size(), responseWithoutName.getUsers().size());
  }

  @Test
  @DisplayName("Should be get by id user")
  public void should_be_get_by_id_user() {

    UserEntity user = new UserEntity();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    userService.getById(any());

    verify(userRepository, times(1)).findById(any());

  }

  @Test
  @DisplayName("Should not be get by id user")
  public void should_not_be_get_by_id_user() {

    when(userRepository.findById(any())).thenReturn(Optional.empty());

    ValidationExceptionNotFound exception = assertThrows(ValidationExceptionNotFound.class, () -> {
      userService.getById(any());
    });

    String expectedMessage = "Usuário não encontrado";
    assertEquals(expectedMessage, exception.getMessage());

    verify(userRepository, times(1)).findById(any());
  }
}
