package br.com.meli.supermercado.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {

  public ValidationException(String httpStatusName, String mensagem, int httpStatusNumber) {
    super(mensagem);
    this.httpStatusName = httpStatusName;
    this.httpStatusNumber = httpStatusNumber;
  }

  private String httpStatusName;
  private int httpStatusNumber;

}
