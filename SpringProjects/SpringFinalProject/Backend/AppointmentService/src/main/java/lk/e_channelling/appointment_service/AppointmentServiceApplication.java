package lk.e_channelling.appointment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
public class AppointmentServiceApplication {

	public static final String DOMAIN_CLIENT_SERVICE = "localhost:8040";
	public static final String DOMAIN_CHANNELLING_SERVICE = "localhost:8060";
	public static final String OAUTH_CLIENT_ID = "mobile";
	public static final String OAUTH_CLIENT_SECRET = "pin";

	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

}
