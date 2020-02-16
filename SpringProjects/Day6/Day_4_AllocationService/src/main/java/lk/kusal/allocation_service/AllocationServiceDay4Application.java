package lk.kusal.allocation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AllocationServiceDay4Application {

	public static void main(String[] args) {
		SpringApplication.run(AllocationServiceDay4Application.class, args);
	}

}
