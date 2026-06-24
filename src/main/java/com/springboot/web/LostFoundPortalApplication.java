package com.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LostFoundPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LostFoundPortalApplication.class, args);
	}

}