package com.ibm.CardService.ServiceGetCard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceGetCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceGetCardApplication.class, args);
	}

}
