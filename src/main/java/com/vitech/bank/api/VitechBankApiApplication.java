package com.vitech.bank.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class VitechBankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitechBankApiApplication.class, args);
	
	}
    
	// Annotation  
	@Bean
	public RestTemplate restTemplate() 
	{
	 return new RestTemplate();
	}
}
