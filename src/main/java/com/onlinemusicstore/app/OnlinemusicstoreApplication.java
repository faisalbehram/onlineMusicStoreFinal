package com.onlinemusicstore.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class OnlinemusicstoreApplication {

	public static void main(String[] args)  {
		SpringApplication.run(OnlinemusicstoreApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
