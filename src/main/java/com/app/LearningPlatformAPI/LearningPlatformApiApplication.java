package com.app.LearningPlatformAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Learning Platform API \n Master or Manager Login id : tamilkumaran001@gmail.com\" +\n,Password: Tamil",
		description = "In this platform i have created a basic and simple Learning application, I have implemented multiple roles in this application. this a beta version i am current working to improving it" +
				"\n Master or Manager Login id : tamilkumaran001@gmail.com" +
				",\n Password: Tamil",
		version = "1.0.0 Beta",
		contact = @Contact(
				name = "Tamilkumaran",
				email = "tamilkumaran021@gmail.com"
		)
))
public class LearningPlatformApiApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformApiApplication.class, args);
	}

}
