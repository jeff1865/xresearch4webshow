package com.yg.webshow.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.yg.webshow.util.DateUtil;

public class ContentFilterTable extends AbstractTable {
	public static final String TN_CRAWL = "DocTemplateTemp";
	public static final String CF_MAIN = "cr1";
	public static final String CQ_DT_INIT = "ist";
	public static final String CQ_DT_LATEST = "lst";
	public static final String CQ_CNT = "cnt";
	public static final String CQ_VALUE = "val";
			
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
//		Map<String, String> values = new HashMap<String, String>();
//		values.put(CQ_DT_INIT, DateUtil.getCurrent());
//		values.put(CQ_DT_LATEST, DateUtil.getCurrent());
////		values.put(CQ_CNT, "0");
//		values.put(CQ_VALUE, dataValue);
//		
//		try {
//			this.putString(urlPattern + "::" + nodePath, CF_MAIN, values);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false ;
//		}
		
		
		Map<byte[], byte[]> values = new HashMap<byte[], byte[]>();
		values.put(Bytes.toBytes(CQ_DT_INIT), Bytes.toBytes(DateUtil.getCurrent()));
		values.put(Bytes.toBytes(CQ_DT_LATEST), Bytes.toBytes(DateUtil.getCurrent()));
		values.put(Bytes.toBytes(CQ_CNT), Bytes.toBytes((long)99));
		values.put(Bytes.toBytes(CQ_VALUE), Bytes.toBytes(dataValue));
		try {
			this.put(urlPattern + "::" + nodePath, CF_MAIN, values);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return false;
	}
	
	public String getDataValue(String key) {
		return null;
	}
	
	private long increseCnt(String urlPattern, String nodePath) {
		try {
			long resVal = this.getTable().incrementColumnValue(
					Bytes.toBytes(urlPattern + "::" + nodePath), 
					Bytes.toBytes(CF_MAIN), 
					Bytes.toBytes(CQ_CNT),
					(long)1);
			System.out.println("Result >>> " + resVal);
			return resVal;
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return -1;
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
//			cfTbl.createTable(CF_MAIN);
//			System.out.println("Successfully table created ..");
			
//			cfTbl.updateNode("http://x.x.com/a=d[10]&c=s[3]", "/xhtml:1/xbody:4/xdiv:2/#text/xxx", "Hello xWorld");
//			System.out.println("Successfully data updated ..");
			
			cfTbl.increseCnt("http://x.x.com/a=d[10]&c=s[3]", "/xhtml:1/xbody:4/xdiv:2/#text/xxx");
			System.out.println("Successfully Increased ..");
			
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
