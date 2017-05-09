package com.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.search.mapper")
@RestController
public class SearchApplication {

	@Value("${SOLR_ZK_HOST}")
	private String config;

	@RequestMapping("/searchconfig")
	public String get(){
		return config;
	}

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}
}
