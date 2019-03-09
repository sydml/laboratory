package com.sydml.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sydml.laboratory")
public class LaboratoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaboratoryApplication.class, args);
	}

}
