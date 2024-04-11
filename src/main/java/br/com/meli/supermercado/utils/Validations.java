package br.com.meli.supermercado.utils;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import lombok.Data;

@Data
@Service
public class Validations {

  public Boolean under18YearsOld(LocalDate dateOfBirth) {

    LocalDate now = LocalDate.now();
    LocalDate minDate = now.minusYears(18);

    return dateOfBirth.isAfter(minDate);
  }

}
