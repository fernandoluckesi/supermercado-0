package br.com.meli.supermercado.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

  public static String objectToJson(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.findAndRegisterModules();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
