package lk.e_channelling.patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ClientServiceApplication {

	public static int CLIENT_NAME_MIN_LENGTH = 4;
	public static int CLIENT_TYPE_ADMIN = 1;
	public static int CLIENT_TYPE_MANAGER = 2;
	public static int CLIENT_TYPE_CUSTOMER = 3;
	public static final String DOMAIN_OAUTH_SERVICE = "localhost:9191";

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
