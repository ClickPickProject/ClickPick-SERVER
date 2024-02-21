package com.clickpick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ClickpickApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickpickApplication.class, args);
	}

}
