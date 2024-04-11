package br.com.meli.supermercado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Supermercado Mercado Livre", description = "API Supermercado do Mercado Livre", version = "1"))
public class SupermercadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermercadoApplication.class, args);
	}

}
