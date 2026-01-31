package com.example.e5i2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class E5i2Application {

	public static void main(String[] args) {
		SpringApplication.run(E5i2Application.class, args);
	}

}
