package br.com.meli.supermercado.dtosSwagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrorUserNotFoundDTO {

  @Schema(example = "BAD_REQUEST")
  private String error;

  @Schema(example = "Usuário não encontrado")
  private String message;

  @Schema(example = "404")
  private Integer statusCode;

}
