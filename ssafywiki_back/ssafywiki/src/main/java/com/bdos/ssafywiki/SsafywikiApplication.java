package com.bdos.ssafywiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SsafywikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsafywikiApplication.class, args);
	}

}
