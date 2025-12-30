package com.br.estante_virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EstanteVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstanteVirtualApplication.class, args);
	}

}
