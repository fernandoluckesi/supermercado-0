package br.com.meli.supermercado.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.meli.supermercado.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  List<UserEntity> findByCompleteNameLikeIgnoreCase(String completeName);

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByCpf(String cpf);

}