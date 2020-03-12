package lk.e_channelling.patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientServiceApplication {

	public static int CLIENT_NAME_MIN_LENGTH = 4;

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
