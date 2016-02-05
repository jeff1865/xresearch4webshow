package com.yg.webshow.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
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
	
	public static final long CD_NEW_TEXT_NODE = 0;
	
	public ContentFilterTable(Connection conn) {
		super(conn);
	}
	
	public ContentFilterTable() {
		super();
	}
	
	/**
	 * Scan & Display all data
	 */
	public void displayAll() {
		ResultScanner scn = null;
		try {
			scn = this.getTable().getScanner(new Scan());
			Result res = null;
			int i = 0;
			while((res = scn.next()) != null) {
				try { 
					String anchorText = Bytes.toString(res.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_VALUE)));
					long hitCnt = Bytes.toLong(res.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_CNT)));
					System.out.println(i++ + "\t" + hitCnt + "\t" + anchorText + " --->" + res);
				} catch(Exception e) {
//					e.printStackTrace();
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
		} finally {
			if(scn != null) scn.close();
		}
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
	public long updateNode(String urlPattern, String nodePath, String dataValue) {
		// Check if the same row(Key) exists or not
		ContentFilterRow value = this.getDataValue(urlPattern, nodePath);
		
		// Put New Data
		if(value == null) {
			
			Map<byte[], byte[]> values = new HashMap<byte[], byte[]>();
			values.put(Bytes.toBytes(CQ_DT_INIT), Bytes.toBytes(DateUtil.getCurrent()));
			values.put(Bytes.toBytes(CQ_DT_LATEST), Bytes.toBytes(DateUtil.getCurrent()));
			values.put(Bytes.toBytes(CQ_CNT), Bytes.toBytes((long)0));
			values.put(Bytes.toBytes(CQ_VALUE), Bytes.toBytes(dataValue));
			try {
				this.put(urlPattern + "::" + nodePath, Bytes.toBytes(CF_MAIN), values);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return CD_NEW_TEXT_NODE;
		} else {	// Update Data
			if(value.getValue().equals(dataValue)) {
				return this.increseCnt(urlPattern, nodePath);
			} else {
				// Different TextNode Exist
				return CD_NEW_TEXT_NODE;
			}
		}
	}
	
	public ContentFilterRow getDataValue(String urlPattern , String nodePath) {
		ContentFilterRow cfRow = null ;
		try {
			Map<String, byte[]> resMap = this.get(urlPattern + "::" + nodePath, CF_MAIN, CQ_VALUE, CQ_CNT, CQ_DT_LATEST, CQ_DT_INIT);
			if(resMap != null) {
				cfRow = new ContentFilterRow() ;
				
				cfRow.setCnt(Bytes.toLong(resMap.get(CQ_CNT)));
				cfRow.setValue(Bytes.toString(resMap.get(CQ_VALUE)));
				cfRow.setInitAt(Bytes.toString(resMap.get(CQ_DT_INIT)));
				cfRow.setLatestAt(Bytes.toString(resMap.get(CQ_DT_LATEST)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cfRow;
	}
	
	/**
	 * Increase by 1 of cnt value
	 * @param urlPattern
	 * @param nodePath
	 * @return
	 */
	private long increseCnt(String urlPattern, String nodePath) {
		try {
			long resVal = this.getTable().incrementColumnValue(
					Bytes.toBytes(urlPattern + "::" + nodePath), 
					Bytes.toBytes(CF_MAIN), 
					Bytes.toBytes(CQ_CNT),
					(long)1);
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
			
//			cfTbl.increseCnt("http://x.x.com/a=d[10]&c=s[3]", "/xhtml:1/xbody:4/xdiv:2/#text/xxx");
//			System.out.println("Successfully Increased ..");
//			
//			ContentFilterRow dVal = cfTbl.getDataValue("http://x.x.com/a=d[10]&c=s[3]", "/xhtml:1/xbody:4/xdiv:2/#text/xxx");
//			System.out.println("Result >>> " + dVal);
			
			cfTbl.displayAll();
			
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
