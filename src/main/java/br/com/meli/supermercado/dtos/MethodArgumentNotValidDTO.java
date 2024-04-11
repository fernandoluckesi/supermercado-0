package br.com.meli.supermercado.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MethodArgumentNotValidDTO {

  private String message;
  private List<ErrorDTO> errors;
  private Integer statusCode;

  @Data
  @AllArgsConstructor
  public static class ErrorDTO {
    private String message;
    private String field;
  }

}
