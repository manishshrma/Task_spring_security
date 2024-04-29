package com.eminence.innovation.task.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Template {
	
	@Bean
	 RestTemplate getRestTemplate() {
		return new RestTemplate();
		
	}
}
