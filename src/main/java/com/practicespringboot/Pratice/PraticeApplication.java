package com.practicespringboot.Pratice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PraticeApplication {

	@GetMapping("/test")
	public String getMessage(){
		return "Hello Tarun!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(PraticeApplication.class, args);
	}

}
