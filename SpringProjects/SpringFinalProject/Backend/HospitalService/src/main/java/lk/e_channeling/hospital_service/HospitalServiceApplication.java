package lk.e_channeling.hospital_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HospitalServiceApplication {

	public static final String DOMAIN_CLIENT_SERVICE = "localhost:8040";

	public static final byte NAME_MIN_LENGTH = 4;

	public static void main(String[] args) {
		SpringApplication.run(HospitalServiceApplication.class, args);
	}

}
