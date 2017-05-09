package com.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/9.
 */
@RestController
public class ConfigController {

    @Value("${spring.redis.cache.clusterNodes}")
    private String config;

    @RequestMapping("/config")
    public String config(){
        return config;
    }
}
