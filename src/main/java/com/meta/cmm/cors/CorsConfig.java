package com.meta.cmm.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:3000", "http://localhost:3001","http://127.0.0.1:3001","http://221.149.98.248:3001","http://172.26.144.1:3001","https://localhost:3001","https://192.168.0.4:3001")
			.allowedMethods("*")
			.allowedHeaders("*")
			.exposedHeaders("X-AUTH-TOKEN")
			.allowCredentials(false)
			.maxAge(60*60); // 1 hour
	}
}