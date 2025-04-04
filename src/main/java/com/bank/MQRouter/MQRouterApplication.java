package com.bank.MQRouter;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
						.allowedHeaders("Authorization", "Content-Type") // Tous les headers
						.allowCredentials(true); // Autoriser les cookies et authentifications
			}
		};
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Mon API")
						.version("1.0")
						.description("API REST pour gérer les messages et partenaires"));
	}

}
