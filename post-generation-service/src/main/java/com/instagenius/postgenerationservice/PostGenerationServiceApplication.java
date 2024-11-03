package com.instagenius.postgenerationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class PostGenerationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostGenerationServiceApplication.class, args);
	}

}
