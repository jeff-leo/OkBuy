package com.rest.config;

import javafx.scene.effect.SepiaTone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/6.
 * Redis Cluster的配置类
 */
@Configuration
@ConditionalOnClass(JedisCluster.class)
public class RedisClusterConfig {

    @Value("${redis.clusterNodes}")
    String clusterNodes;

    @Value("${redis.commandTimeout}")
    String timeout;

    @Bean
    public JedisCluster getJedisCluster(){
        String[] serverArray = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();

        for (String s: serverArray) {
            String[] hostport = s.split(":");
            nodes.add(new HostAndPort(hostport[0], Integer.valueOf(hostport[1])));
        }
        return new JedisCluster(nodes, Integer.valueOf(timeout));
    }
}
