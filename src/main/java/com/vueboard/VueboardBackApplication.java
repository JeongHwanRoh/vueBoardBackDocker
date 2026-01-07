package com.vueboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vueboard.domains.**.mapper")
public class VueboardBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueboardBackApplication.class, args);
	}

}
