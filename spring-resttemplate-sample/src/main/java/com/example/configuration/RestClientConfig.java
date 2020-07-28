package com.example.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.interceptor.RestTemplateLoggingInterceptor;


@Configuration
public class RestClientConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.additionalInterceptors(new RestTemplateLoggingInterceptor())
				.build();
	}
}
