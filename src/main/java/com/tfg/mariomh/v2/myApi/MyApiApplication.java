package com.tfg.mariomh.v2.myApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApiApplication.class, args);
	}

}
