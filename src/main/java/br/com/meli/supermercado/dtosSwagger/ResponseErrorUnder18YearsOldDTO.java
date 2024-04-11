package br.com.meli.supermercado.dtosSwagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrorUnder18YearsOldDTO {

  @Schema(example = "BAD_REQUEST")
  private String error;

  @Schema(example = "Usuário deve ter 18 anos ou mais")
  private String message;

  @Schema(example = "400")
  private Integer statusCode;

}
