package com.rest;

import com.mapperutils.entity.TbItem;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mapperutils.mapper")
@RestController
public class RestApplication {

	@Value("${REDIS_ITEM_KEY}")
	private String key;

	@RequestMapping("/get")
	public String get(){
		return key;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
}
