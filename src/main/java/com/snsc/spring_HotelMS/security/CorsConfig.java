package com.snsc.spring_HotelMS.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration				//This code defines a CORS (Cross-Origin Resource Sharing) configuration for a Spring Boot application.				
public class CorsConfig {
	@Bean
	public WebMvcConfigurer WebMvcConfigurer() {
		return new WebMvcConfigurer() {
            @Override			//Preventing CORS errors when the frontend communicates with the backend.
			public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE")
	                        .allowedOrigins("*");
		}
		
	};

}
}