package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class ContentFilterTable extends AbstractTable {
	public static final String TN_CRAWL = "DocTemplateTemp";
	public static final String CF_MAIN = "cr1";
	public static final String CQ_CNT = "cnt";
	public static final String CQ_DT_TBD = "TBD";
	
		
	public ContentFilterTable(Connection conn) {
		super(conn);
	}
	
	/**
	 * Put data, if the same key doesn't exist.
	 * Append data and increase count, if the same data dosen't exit for same key
	 * 
	 * 
	 * @param urlPattern
	 * @param nodePath
	 * @param dataValue
	 * @return
	 */
	public boolean updateNode(String urlPattern, String nodePath, String dataValue) {
		
		return false;
	}
	
	public String getDataValue(String urlPattern, String nodePath) {
		return null;
	} 
	
	@Override
	protected String getTableName() {
		return TN_CRAWL;
	}
	
	public static void main(String ... v) {
		System.out.println("Test .. " + System.currentTimeMillis());
		
		Connection conn = null;
		try {
			Configuration config = HBaseConfiguration.create();
			config.set("hbase.master", "127.0.0.1");
			conn = ConnectionFactory.createConnection(config);
			
			ContentFilterTable cfTbl = new ContentFilterTable(conn);
			cfTbl.createTable(CF_MAIN);
			System.out.println("Successfully table created ..");
			cfTbl.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}
