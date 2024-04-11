package br.com.meli.supermercado.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

  @Schema(example = "Bruce Wayne", requiredMode = RequiredMode.REQUIRED)
  @NotBlank(message = "O nome completo não pode estar vazio")
  @Column(nullable = false, unique = false)
  private String completeName;

  @Schema(example = "password12345678910", requiredMode = RequiredMode.REQUIRED)
  @NotBlank(message = "A senha não pode estar vazia")
  @Length(min = 10, message = "A senha deve ter no mínimo 10 caracteres")
  @Column(nullable = false, unique = false)
  private String password;

  @Schema(example = "example@email.com", requiredMode = RequiredMode.REQUIRED)
  @Column(nullable = false, unique = true)
  @Email(message = "Informe um e-mail válido")
  private String email;

  @Schema(example = "27815173055", requiredMode = RequiredMode.REQUIRED)

  @CPF(message = "Número do CPF inválido")
  @Column(nullable = false, unique = true)
  @Pattern(regexp = "\\d*", message = "CPF deve conter apenas números")
  private String cpf;

  @Schema(example = "1994-03-25", requiredMode = RequiredMode.REQUIRED)
  @NotNull
  @Column(nullable = false, unique = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;

}
