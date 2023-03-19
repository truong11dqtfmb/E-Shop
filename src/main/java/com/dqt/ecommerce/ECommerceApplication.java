package com.dqt.ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

}
