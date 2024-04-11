package br.com.meli.supermercado.dtosSwagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrorUserAlreadyExistsDTO {

  @Schema(example = "CONFLICT")
  private String error;

  @Schema(example = "Usuário já está cadastrado com esse e-mail")
  private String message;

  @Schema(example = "409")
  private Integer statusCode;

}
