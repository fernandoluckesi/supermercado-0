package br.com.meli.supermercado.dtosSwagger;

import java.time.LocalDateTime;
import java.util.List;

import br.com.meli.supermercado.entities.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserListGetAllResponseDTO {
  private LocalDateTime timestamp;

  private List<UserEntity> users;

  @Schema(example = "1")
  private Integer totalUsersResults;

}
