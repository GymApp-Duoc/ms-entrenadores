package com.gymapp.ms_entrenadores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsEntrenadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEntrenadoresApplication.class, args);
	}

}
