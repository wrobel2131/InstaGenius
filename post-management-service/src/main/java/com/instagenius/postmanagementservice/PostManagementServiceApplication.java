package com.instagenius.postmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
class PostManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostManagementServiceApplication.class, args);
	}

}
