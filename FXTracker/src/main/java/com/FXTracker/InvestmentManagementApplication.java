package com.FXTracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InvestmentManagementApplication {

	@Bean
	public ObjectMapper objectMapper(){

		var objectMapper = new ObjectMapper();

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		return objectMapper;
	}

	public static void main(String[] args) {

		SpringApplication.run(InvestmentManagementApplication.class, args);
	}

}
