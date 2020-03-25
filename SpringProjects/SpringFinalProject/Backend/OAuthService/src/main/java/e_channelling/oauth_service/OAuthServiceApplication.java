package e_channelling.oauth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class OAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthServiceApplication.class, args);
	}

}
