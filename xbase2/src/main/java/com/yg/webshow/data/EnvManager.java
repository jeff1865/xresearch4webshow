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
import org.apache.log4j.Logger;

public class EnvManager {
	private static Logger log = Logger.getLogger(EnvManager.class);
	private Configuration config = null;
	
	public EnvManager() {
		this.config = HBaseConfiguration.create();
		this.config.set("hbase.master", "127.0.0.1");
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
		
	public static void main (String ... v) {
		EnvManager test = new EnvManager();
		
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("xtest" + System.currentTimeMillis()));
		HColumnDescriptor cf = new HColumnDescriptor("cf");
		tableDesc.addFamily(cf);
		
		test.createNewTable(tableDesc);
		System.out.println("Successfully created .. " + System.currentTimeMillis());
	}
}
