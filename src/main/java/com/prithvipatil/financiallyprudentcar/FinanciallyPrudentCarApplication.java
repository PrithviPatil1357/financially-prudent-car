package com.prithvipatil.financiallyprudentcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FinanciallyPrudentCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanciallyPrudentCarApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000","https://financially-prudent-car.firebaseapp.com","https://financially-prudent-car.web.app")
						.allowedMethods("*");
			}
		};
	}
}