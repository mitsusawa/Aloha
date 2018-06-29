package com.product.aloha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller

public class AlohaApplication {
	@RequestMapping(value = {"/", "/index"})
	public String index() {
		return "index";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AlohaApplication.class, args);
	}
}
