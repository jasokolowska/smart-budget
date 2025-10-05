package com.sokolowska.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.sokolowska.app",
		"com.sokolowska.transactions"
})
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);


	}

}
