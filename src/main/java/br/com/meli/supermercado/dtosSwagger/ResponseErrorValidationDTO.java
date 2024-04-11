package br.com.meli.supermercado.dtosSwagger;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrorValidationDTO {

  @Schema(example = "Erros de dados informados")
  private String message;

  private List<ValidationErrorDto> errors;

  @Schema(example = "400")
  private Integer statusCode;

}
