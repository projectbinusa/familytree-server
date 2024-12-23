package com.project.familytree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.project.familytree")
public class FamilytreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilytreeApplication.class, args);
	}
}
