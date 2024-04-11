package br.com.meli.supermercado.dtosSwagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDeleteResponseDTO {

  @Schema(example = "Usuário deletado com sucesso")
  private String message;

  @Schema(example = "200")
  private Integer statusCode;

}
