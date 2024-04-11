package br.com.meli.supermercado.dtosSwagger;

import java.time.LocalDateTime;

import br.com.meli.supermercado.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGetByIdResponseDTO {

  private LocalDateTime timestamp;

  private UserEntity user;

}
