package com.pravar.Agilysys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pravar.Agilysys")
public class AgilysysApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgilysysApplication.class, args);
	}

}
