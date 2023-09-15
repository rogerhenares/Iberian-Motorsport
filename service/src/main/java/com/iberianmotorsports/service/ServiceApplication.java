package com.iberianmotorsports.service;

import com.iberianmotorsports.service.model.parsing.export.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(Entry.class)
@SpringBootApplication
@RequiredArgsConstructor
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}
