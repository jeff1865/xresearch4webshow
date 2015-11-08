package com.yg.projectx.research;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class Main {
	
	public static void main(String ... v) {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.master", "127.0.0.1");	//Test
				
		Connection conn = null;
		try {
			conn = ConnectionFactory.createConnection(config);
			Admin admin = conn.getAdmin();
			
			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("test" + System.currentTimeMillis()));
			tableDesc.addFamily(new HColumnDescriptor("personal"));
			tableDesc.addFamily(new HColumnDescriptor("professional"));
			
			admin.createTable(tableDesc);
			System.out.println("Successfully Column Created ..");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
//		HTable hTbl = new HTable();
		
		
//		// Instantiating HbaseAdmin class
//	      HBaseAdmin admin = new HBaseAdmin(con);
//
//	      // Instantiating table descriptor class
//	      HTableDescriptor tableDescriptor = new TableDescriptor(TableName.valueOf("emp"));
//
//	      // Adding column families to table descriptor
//	      tableDescriptor.addFamily(new HColumnDescriptor("personal"));
//	      tableDescriptor.addFamily(new HColumnDescriptor("professional"));
//
//	      // Execute the table through admin
//	      admin.createTable(tableDescriptor);
	      System.out.println(" Table created ");
	}
	
}
