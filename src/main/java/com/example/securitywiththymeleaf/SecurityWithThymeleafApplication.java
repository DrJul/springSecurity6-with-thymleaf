package com.example.securitywiththymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.lang.invoke.StringConcatFactory;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // for disabling security
public class SecurityWithThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityWithThymeleafApplication.class, args);
	}

}
