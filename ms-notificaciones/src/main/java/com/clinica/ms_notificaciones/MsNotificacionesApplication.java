package com.clinica.ms_notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsNotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificacionesApplication.class, args);
	}

}
