package br.com.meli.supermercado.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationExceptionBadRequest extends ValidationException {

  public ValidationExceptionBadRequest(String httpStatusName, String mensagem, int httpStatusNumber) {
    super(httpStatusName, mensagem, httpStatusNumber);
  }
}
