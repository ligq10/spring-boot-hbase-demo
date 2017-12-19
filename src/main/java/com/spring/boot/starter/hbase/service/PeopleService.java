package com.spring.boot.starter.hbase.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.starter.hbase.helper.HBaseHelper;

@Service
public class PeopleService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private HBaseHelper hBaseHelper;


    public void createTable(String tableName,String family) throws IOException{

        // 2.1创建表  
        tableName = "blog";  
        hBaseHelper.deleteTable(tableName);  
        String colFamilies[] = { "article", "author" };  
        hBaseHelper.createTable(tableName, colFamilies); 
    }
    
    public void saveRecords(String tableName) throws IOException{

    	   // 2.2插入一条记录  
        tableName = "blog";  
        hBaseHelper.insertRecord(tableName, "1", "article", "title", "Hadoop学习资料");  
        hBaseHelper.insertRecord(tableName, "1", "author", "name", "hugengyong");  
        hBaseHelper.insertRecord(tableName, "1", "article", "content", "Hadoop学习，HBase学习-http://blog.csdn.net/hugengyong");  
    }
    
    public void findOne(String tableName,String rowKey) throws Exception{

        tableName = "blog";  
        // 2.3查询一条记录  
        Result rs1 = hBaseHelper.getOneRecord(tableName, "1");  
        for (KeyValue kv : rs1.raw()) {  
        	logger.info(new String(kv.getRow()));  
        	logger.info(new String(kv.getFamily()));  
        	logger.info(new String(kv.getQualifier()));  
        	logger.info(new String(kv.getValue()));  
        } 
    }
    
    public void findAll(String tableName) throws IOException{

        tableName = "blog";  
        // 2.4查询整个Table  
        List<Result> list = null;  
        list = hBaseHelper.getAllRecord(tableName);  
        Iterator<Result> it = list.iterator();  
        while (it.hasNext()) {  
            Result rs2 = it.next();  
            for (KeyValue kv : rs2.raw()) {  
            	logger.info("row key is : " + new String(kv.getRow()));  
            	logger.info("family is  : " + new String(kv.getFamily()));  
            	logger.info("qualifier is:"  
                        + new String(kv.getQualifier()));  
            	logger.info("timestamp is:" + kv.getTimestamp());  
            	logger.info("Value  is  : " + new String(kv.getValue()));  
            }  
  
        } 
    }
    
}
