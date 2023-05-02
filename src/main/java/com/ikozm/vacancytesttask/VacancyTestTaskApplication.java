package com.ikozm.vacancytesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VacancyTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyTestTaskApplication.class, args);
	}

}
