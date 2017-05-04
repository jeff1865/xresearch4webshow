package com.ygsoft.webshow.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@EnableAutoConfiguration
public class Hell {
	
	@RequestMapping("/")
	public String home() {
		return "Hello World" + System.currentTimeMillis();
	}
	
	@RequestMapping("/hell")
	public String hellHome(Model model) {
//		return "Hello World" + System.currentTimeMillis();
		model.addAttribute("msg", "Hello World" + System.currentTimeMillis());
		return "hell";
	}
	
	public static void main(String ... v) {
		System.out.println("Start System ..");
		SpringApplication.run(Hell.class, v) ;
	}
}
