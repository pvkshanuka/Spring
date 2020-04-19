package lk.e_channelling.doctor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DoctorServiceApplication {

	public static final String DOMAIN_CATEGORY_SERVICE = "localhost:8030/category";
	public static final byte NAME_MIN_LENGTH = 4;
	public static final byte CONTACT_MAX_LENGTH = 10;
	public static final byte CONTACT_MIN_LENGTH = 9;

	public static void main(String[] args) {
		SpringApplication.run(DoctorServiceApplication.class, args);
	}

}
