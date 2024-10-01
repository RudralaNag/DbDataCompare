package com.americanexpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class DbDataCompareApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbDataCompareApplication.class, args);
	}

}
