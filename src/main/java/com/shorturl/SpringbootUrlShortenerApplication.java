package com.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringbootUrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootUrlShortenerApplication.class, args);
	}

}
