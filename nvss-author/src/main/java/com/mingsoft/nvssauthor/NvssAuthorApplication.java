package com.mingsoft.nvssauthor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mingsoft.nvssauthor.mapper")
public class NvssAuthorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NvssAuthorApplication.class, args);
	}

}
