package com.multi.quizwiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class QuizwikiApplication extends SpringBootServletInitializer{
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QuizwikiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizwikiApplication.class, args);
	}

}
