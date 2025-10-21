package com.project.minibankingsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MinibankingsystemApplication {

	@Value("${PORT:8080}")
	public int port;

	public static void main(String[] args) {
		SpringApplication.run(MinibankingsystemApplication.class, args);
	}

	@PostConstruct
	public void printPort() {
		System.out.println(">> Running on port: " + port);
	}

}
