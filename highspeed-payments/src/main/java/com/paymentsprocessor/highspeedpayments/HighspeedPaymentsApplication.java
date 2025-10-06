package com.paymentsprocessor.highspeedpayments;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling; 

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HighspeedPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighspeedPaymentsApplication.class, args);
	}

}

