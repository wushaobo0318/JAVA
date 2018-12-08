package com.frogwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForgWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForgWalletApplication.class, args);
	}
}
