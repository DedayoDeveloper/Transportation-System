package com.insurance.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TravelApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(TravelApplication.class, args);
	}

}
