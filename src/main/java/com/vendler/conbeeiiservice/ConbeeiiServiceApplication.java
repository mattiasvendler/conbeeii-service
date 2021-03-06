package com.vendler.conbeeiiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ConbeeiiServiceApplication {
	@Autowired
	private ConbeeIIService service;
	public static void main(String[] args) {
		SpringApplication.run(ConbeeiiServiceApplication.class, args);

	}
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Snepp");
			service.start();
		};
	}
}
