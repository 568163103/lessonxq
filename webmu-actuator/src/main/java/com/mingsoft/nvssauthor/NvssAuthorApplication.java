package com.mingsoft.nvssauthor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.mingsoft.nvssauthor.mapper")
@EnableScheduling
//@EnableDiscoveryClient
public class NvssAuthorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NvssAuthorApplication.class, args);
	}

}
