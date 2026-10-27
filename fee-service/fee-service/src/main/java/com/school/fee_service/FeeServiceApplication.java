package com.school.fee_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeeServiceApplication.class, args);
	}
}