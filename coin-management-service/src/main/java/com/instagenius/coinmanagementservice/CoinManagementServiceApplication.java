package com.instagenius.coinmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
class CoinManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinManagementServiceApplication.class, args);
	}

}
