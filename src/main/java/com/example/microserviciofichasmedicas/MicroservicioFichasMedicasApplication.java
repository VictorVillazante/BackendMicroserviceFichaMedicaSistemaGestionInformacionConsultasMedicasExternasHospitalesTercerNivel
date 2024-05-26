package com.example.microserviciofichasmedicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MicroservicioFichasMedicasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioFichasMedicasApplication.class, args);
	}

}
