package com.ibm.EurekaServer.EurekaServerProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerProjectApplication.class, args);
	}

}
