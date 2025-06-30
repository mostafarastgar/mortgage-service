package com.mostafa.ing.mortgage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MortgageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortgageServiceApplication.class, args);
	}

}
