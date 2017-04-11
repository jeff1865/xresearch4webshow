package com.ygsoft.webshow.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableAutoConfiguration
public class Hell {
	
	@RequestMapping("/")
	String home() {
		return "Hello World" + System.currentTimeMillis();
	}
	
	public static void main(String ... v) {
		System.out.println("Start System ..");
		SpringApplication.run(Hell.class, v) ;
	}
}
