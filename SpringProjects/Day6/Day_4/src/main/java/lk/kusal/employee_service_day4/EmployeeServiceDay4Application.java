package lk.kusal.employee_service_day4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class EmployeeServiceDay4Application {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceDay4Application.class, args);
	}

}
