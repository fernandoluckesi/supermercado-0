package br.com.meli.supermercado.exceptions;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.meli.supermercado.dtos.ApiError;
import br.com.meli.supermercado.dtos.MethodArgumentNotValidDTO;
import br.com.meli.supermercado.dtos.MethodArgumentNotValidDTO.ErrorDTO;
import lombok.Builder;

@ControllerAdvice
@Builder
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<MethodArgumentNotValidDTO> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    List<ErrorDTO> errorsList = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(err -> {
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      ErrorDTO error = new ErrorDTO(message, err.getField());
      errorsList.add(error);
    });

    MethodArgumentNotValidDTO methodArgumentNotValid = new MethodArgumentNotValidDTO("Erros de dados informados",
        errorsList, HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(methodArgumentNotValid, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ApiError> handleDateTimeParseException(
      DateTimeParseException e) {
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.name(), "Data de nascimento inválida",
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ValidationExceptionNotFound.class)
  public ResponseEntity<ApiError> handleValidationExceptionNotFound(ValidationExceptionNotFound e) {

    ApiError error = new ApiError(e.getHttpStatusName(), e.getMessage(), e.getHttpStatusNumber());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationExceptionBadRequest.class)
  public ResponseEntity<ApiError> handleValidationExceptionUnder18YearsOld(ValidationExceptionBadRequest e) {

    ApiError error = new ApiError(e.getHttpStatusName(), e.getMessage(), e.getHttpStatusNumber());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ValidationExceptionConflict.class)
  public ResponseEntity<ApiError> handleValidationExceptionConflict(ValidationExceptionConflict e) {

    ApiError error = new ApiError(e.getHttpStatusName(), e.getMessage(), e.getHttpStatusNumber());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

    ApiError error = new ApiError(HttpStatus.CONFLICT.name(), "Usuário já cadastrado",
        HttpStatus.CONFLICT.value());

    return new ResponseEntity<>(error, HttpStatus.CONFLICT);

  }

}
