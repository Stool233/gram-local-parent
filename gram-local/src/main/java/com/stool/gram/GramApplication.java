package com.stool.gram;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GramApplication {
	public static void main(String[] args) {
		SpringApplication.run(GramApplication.class, args);
	}
}
