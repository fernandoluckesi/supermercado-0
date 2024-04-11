package br.com.meli.supermercado.dtosSwagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorDto {

  @Schema(example = "Número do CPF inválido")
  private String message;

  @Schema(example = "cpf")
  private String field;

}