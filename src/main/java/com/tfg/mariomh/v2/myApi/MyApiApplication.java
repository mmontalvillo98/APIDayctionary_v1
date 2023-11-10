package com.tfg.mariomh.v2.myApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoReactiveAutoConfiguration.class})
@EnableMongoAuditing
public class MyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApiApplication.class, args);
	}

}
