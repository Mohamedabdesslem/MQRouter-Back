package com.bank.MQRouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
public class MQRouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MQRouterApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**") // Autoriser toutes les routes API
						.allowedOrigins("http://localhost:4200") // Autoriser l'origine Angular
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
						.allowedHeaders("*") // Tous les headers
						.allowCredentials(true); // Autoriser les cookies et authentifications
			}
		};
	}

}
