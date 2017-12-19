package com.spring.boot.starter.hbase.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.client.Delete;  
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HBaseHelper {

	@Autowired
    private Configuration conf;// 配置器 
	@Autowired
    private HBaseAdmin admin;// HBase管理员  
  
    /** 
     * 获取HBase配置器 
     *  
     * @param conf 
     *            Hadoop配置器 
     * @throws IOException 
     */  
/*    public HBaseHelper(Configuration conf) throws IOException {  
        this.conf = HBaseConfiguration.create(conf);  
        this.admin = new HBaseAdmin(this.conf);  
        System.out.println("创建HBase配置成功！");  
    } */ 
  
    /** 
     * 获取HBase配置器 
     *  
     * @throws IOException 
     */  
/*    public HBaseHelper() throws IOException {  
        Configuration cnf = new Configuration();  
        this.conf = HBaseConfiguration.create(cnf);  
        this.admin = new HBaseAdmin(this.conf);  
        System.out.println("创建HBase配置成功！");  
    }  */
  
    /** 
     * 创建HBase表 
     *  
     * @param tableName 
     *            表名 
     * @param colFamilies 
     *            列簇 
     * @throws IOException 
     */  
    public void createTable(String tableName, String colFamilies[])  
            throws IOException {  
        if (this.admin.tableExists(tableName)) {  
            System.out.println("Table: " + tableName + " already exists !");  
        } else {  
            HTableDescriptor dsc = new HTableDescriptor(tableName);  
            int len = colFamilies.length;  
            for (int i = 0; i < len; i++) {  
                HColumnDescriptor family = new HColumnDescriptor(colFamilies[i]);  
                dsc.addFamily(family);  
            }  
            admin.createTable(dsc);  
            System.out.println("创建表" + tableName + "成功");  
        }  
  
    }  
  
    /** 
     * 删除表 
     *  
     * @param tableName 
     *            表名 
     * @throws IOException 
     */  
    public void deleteTable(String tableName) throws IOException {  
        if (this.admin.tableExists(tableName)) {  
            admin.disableTable(tableName);  
            System.out.println("禁用表" + tableName + "!");  
            admin.deleteTable(tableName);  
            System.out.println("删除表成功!");  
        } else {  
            System.out.println(tableName + "表不存在 !");  
        }  
    }  
  
    /** 
     * 插入记录 
     *  
     * @param tableName 
     *            表名 
     * @param rowkey 
     *            键 
     * @param family 
     *            簇 
     * @param qualifier 
     * @param value 
     *            值 
     * @throws IOException 
     */  
    public void insertRecord(String tableName, String rowkey, String family,  
            String qualifier, String value) throws IOException {  
        HTable table = new HTable(this.conf, tableName);  
        Put put = new Put(rowkey.getBytes());  
        put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());  
        table.put(put);  
        System.out.println(tableName + "插入key:" + rowkey + "行成功!");  
    }  
  
    /** 
     * 删除一行记录 
     *  
     * @param tableName 
     *            表名 
     * @param rowkey 
     *            主键 
     * @throws IOException 
     */  
    public void deleteRecord(String tableName, String rowkey)  
            throws IOException {  
        HTable table = new HTable(this.conf, tableName);  
        Delete del = new Delete(rowkey.getBytes());  
        table.delete(del);  
        System.out.println(tableName + "删除行" + rowkey + "成功!");  
    }  
  
    /** 
     * 获取一条记录 
     *  
     * @param tableName 
     *            表名 
     * @param rowkey 
     *            主键 
     * @return 
     * @throws IOException 
     */  
    public Result getOneRecord(String tableName, String rowkey)  
            throws IOException {  
        HTable table = new HTable(this.conf, tableName);  
        Get get = new Get(rowkey.getBytes());  
        Result rs = table.get(get);  
        return rs;  
    }  
  
    /** 
     * 获取所有数据 
     * @param tableName 表名 
     * @return 
     * @throws IOException 
     */  
    public List<Result> getAllRecord(String tableName) throws IOException {  
        HTable table = new HTable(this.conf, tableName);  
        Scan scan = new Scan();  
        ResultScanner scanner = table.getScanner(scan);  
        List<Result> list = new ArrayList<Result>();  
        for (Result r : scanner) {  
            list.add(r);  
        }  
        scanner.close();  
        return list;  
    }  
}
