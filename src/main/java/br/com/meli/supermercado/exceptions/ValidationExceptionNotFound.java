package br.com.meli.supermercado.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationExceptionNotFound extends ValidationException {

  public ValidationExceptionNotFound(String httpStatusName, String mensagem, int httpStatusNumber) {
    super(httpStatusName, mensagem, httpStatusNumber);
  }

}
