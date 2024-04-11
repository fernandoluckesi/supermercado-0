package br.com.meli.supermercado.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationExceptionConflict extends ValidationException {

  public ValidationExceptionConflict(String httpStatusName, String mensagem, int httpStatusNumber) {
    super(httpStatusName, mensagem, httpStatusNumber);
  }

}
