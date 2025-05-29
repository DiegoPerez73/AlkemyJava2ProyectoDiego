package com.Java2.proyecto.diego.AlkemyDiego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.Java2.proyecto.diego.AlkemyDiego")
public class AlkemyDiegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlkemyDiegoApplication.class, args);
	}

}
