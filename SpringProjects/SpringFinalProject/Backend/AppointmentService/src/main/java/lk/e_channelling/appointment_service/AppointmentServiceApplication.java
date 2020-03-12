package lk.e_channelling.appointment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointmentServiceApplication {

	public static final String DOMAIN_CLIENT_SERVICE = "localhost:8040";
	public static final String DOMAIN_CHANNELLING_SERVICE = "localhost:8060";

	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

}
