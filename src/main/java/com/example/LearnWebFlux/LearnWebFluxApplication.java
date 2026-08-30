package com.example.LearnWebFlux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableR2dbcAuditing
public class LearnWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnWebFluxApplication.class, args);
	}

}
/*
@EnableR2dbcAuditing enables automatic auditing of database entities when you're using Spring Data R2DBC.

In simple terms, it allows Spring to automatically fill fields like:

createdAt → when the record was created
updatedAt → when the record was last modified
createdBy → who created it
updatedBy → who modified it

*/