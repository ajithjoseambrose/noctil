package com.ajith.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "com.ajith.batch")
public class BatchApplication {

	public BatchApplication() throws JAXBException {
	}

	public static void main(String[] args) throws IOException, JAXBException {
		SpringApplication.run(BatchApplication.class, args);
		System.out.println("running..");
	}

	}