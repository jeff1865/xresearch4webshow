package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.log4j.Logger;

public class EnvManager {
	private static Logger log = Logger.getLogger(EnvManager.class);
	private Configuration config = null;
	
	public EnvManager() {
		this.config = HBaseConfiguration.create();
		this.config.set("hbase.master", "127.0.0.1");
	}
	
	public Connection getNewConnection() {
		try {
			return ConnectionFactory.createConnection(this.config);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void createNewTable(HTableDescriptor tableDesc) {
		Connection conn = null;
				
		try {
			conn = ConnectionFactory.createConnection(this.config);
			Admin admin = conn.getAdmin();
			
			admin.createTable(tableDesc);
			log.info("Successfully table created :" + tableDesc.getTableName().getNameAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void addSampleData() {
		log.info("Add Sample Data ..");
		
		Connection conn = null;
		try {
			conn = ConnectionFactory.createConnection(this.config);
			Table table = conn.getTable(TableName.valueOf("xtest1448969007094"));
			
			Put put = new Put(Bytes.toBytes("ID_001_" + System.currentTimeMillis()));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("AAA001"), Bytes.toBytes("V001_" + System.currentTimeMillis()));
			table.put(put);
			
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void getSampleData() {
		log.info("Add Sample Data ..");
		
		Connection conn = null;
		try {
			conn = ConnectionFactory.createConnection(this.config);
			Table table = conn.getTable(TableName.valueOf("xtest1448969007094"));
			
			Put put = new Put(Bytes.toBytes("ID_001_" + System.currentTimeMillis()));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("AAA001"), Bytes.toBytes("V001_" + System.currentTimeMillis()));
			table.put(put);
			
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	public static void main (String ... v) {
		EnvManager test = new EnvManager();
//		test.addSampleData();
		
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("newslist"));
		HColumnDescriptor cf = new HColumnDescriptor("cr1");
		HColumnDescriptor cf2 = new HColumnDescriptor("cr2");
		tableDesc.addFamily(cf);
		tableDesc.addFamily(cf2);
		test.createNewTable(tableDesc);
		
		System.out.println("Successfully created .. " + System.currentTimeMillis());
	}
}
