package com.search.config;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/5/7.
 */
//solr集群版
//@Configuration
//@ConditionalOnClass(CloudSolrServer.class)
public class CloudSolrServerConfig {

    /*@Value("${SOLR_ZK_HOST}")
    private String zkHost;

    @Bean
    public CloudSolrServer getHttpSolrServer(){
        //配置solr集群的host
        CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);
        //配置solr集群的默认collection
        cloudSolrServer.setDefaultCollection("collection2");
        return cloudSolrServer;
    }*/
}
