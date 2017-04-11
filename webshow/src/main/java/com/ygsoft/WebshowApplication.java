package com.ygsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ygsoft")
public class WebshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshowApplication.class, args);
	}
}
