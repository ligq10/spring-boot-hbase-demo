package com.spring.boot.starter.hbase.config;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseConfig {

    @Autowired
    private HbaseProperties hbaseProperties;
    
    @Bean
	public org.apache.hadoop.conf.Configuration initConfiguration(){
        // 1.初始化HBaseOperation  
		org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();          
        configuration.set(HConstants.ZOOKEEPER_QUORUM, hbaseProperties.getQuorum());
        configuration.set(HConstants.HBASE_DIR, hbaseProperties.getRootDir());
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, hbaseProperties.getClientPort());
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create(configuration);  
        return conf;
	}
	
    @Bean
	public HBaseAdmin initHBaseAdmin() throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
		HBaseAdmin admin = new HBaseAdmin(initConfiguration());
		return admin;
	}
}
