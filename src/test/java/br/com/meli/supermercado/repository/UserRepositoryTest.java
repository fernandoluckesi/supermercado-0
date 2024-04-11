package br.com.meli.supermercado.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.meli.supermercado.dtos.UserDTO;
import br.com.meli.supermercado.entities.UserEntity;
import br.com.meli.supermercado.repositories.UserRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  @DisplayName("Should get user by email from DB")
  void should_get_user_by_email_from_DB() {

    String email = "fernando@gmail.com";

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    this.createUser(data);

    Optional<UserEntity> foundedUser = this.userRepository.findByEmail(email);

    assertThat(foundedUser.isPresent()).isTrue();

  }

  @Test
  @DisplayName("Should get user by cpf from DB")
  void should_get_user_by_cpf_from_DB() {

    String cpf = "42275937862";

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    this.createUser(data);

    Optional<UserEntity> foundedUser = this.userRepository.findByCpf(cpf);

    assertThat(foundedUser.isPresent()).isTrue();

  }

  @Test
  @DisplayName("Should not get user by email from DB")
  void should_not_get_user_by_email_from_DB() {

    String email = "fernando@gmail.com";

    Optional<UserEntity> foundedUser = this.userRepository.findByEmail(email);

    assertThat(foundedUser.isEmpty()).isTrue();

  }

  @Test
  @DisplayName("Should not get user by cpf from DB")
  void should_not_get_user_by_email_or_cpf_like_from_DB() {

    String cpf = "42275937862";

    Optional<UserEntity> foundedUser = this.userRepository.findByCpf(cpf);

    assertThat(foundedUser.isEmpty()).isTrue();

  }

  @Test
  @DisplayName("Should get user by complete name like from DB")
  void should_get_user_by_complete_name_like_from_DB() {

    UserDTO data = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    this.createUser(data);

    String nameFounded = "%ferna%";
    String nameNotFounded = "%bruce%";

    List<UserEntity> foundedUsers = this.userRepository.findByCompleteNameLikeIgnoreCase(nameFounded);
    List<UserEntity> notFoundedUsers = this.userRepository.findByCompleteNameLikeIgnoreCase(nameNotFounded);

    assertThat(foundedUsers.size()).isEqualTo(1);
    assertThat(notFoundedUsers.size()).isEqualTo(0);

  }

  private UserEntity createUser(UserDTO data) {
    UserEntity user = UserEntity.builder()
        .completeName(data.getCompleteName())
        .password(data.getPassword())
        .email(data.getEmail())
        .cpf(data.getCpf())
        .dateOfBirth(data.getDateOfBirth())
        .build();
    this.entityManager.persist(user);
    return user;
  }

}
