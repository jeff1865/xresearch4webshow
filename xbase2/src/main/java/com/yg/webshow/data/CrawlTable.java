package com.yg.webshow.data;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import com.yg.webshow.util.DateUtil;

public class CrawlTable extends AbstractTable {
	public static final String TN_CRAWL = "crawl";
	public static final String CF_MAIN = "cr1";
	public static final String CQ_DT_REG = "reg";
	public static final String CQ_ANCHOR = "ac_txt";
	public static final String CQ_STATUS = "sts";
	
	public static final String VAL_STATUS_INIT = "INIT";
	public static final String VAL_STATUS_SUMMARIZED = "SUMMARIZED";
	public static final String VAL_STATUS_FAILED = "FAILED";
		
	public CrawlTable(Connection conn) {
		super(conn);
	}
		
	public void insertCrawledUrl(String siteId, String url, String anchorText) {
		try {
			Hashtable<String, String> htVal = new Hashtable<String, String>();
			htVal.put(CQ_ANCHOR, anchorText);
			htVal.put(CQ_DT_REG, DateUtil.getCurrent());
			htVal.put(CQ_STATUS, VAL_STATUS_INIT);
			
			this.putString(siteId + "_" + url, CF_MAIN, htVal);
//			this.put(siteId + "_" + url, CF_MAIN, CQ_ANCHOR, anchorText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CrawlRow getRawData(String siteId, String url) {
		CrawlRow resRow = null;
		
		try {
			Map<String, byte[]> res = this.get(siteId + "_" + url, CF_MAIN, CQ_ANCHOR, CQ_DT_REG);
//			System.out.println("-----" + res);;
			
			
			if(res != null && res.get(CQ_DT_REG) != null && res.size() > 0) {
				resRow = new CrawlRow() ;
				resRow.setUrl(url);
				resRow.setSiteId(siteId);
				resRow.setKey(siteId + "_" + url);
				resRow.setAnchor(Bytes.toString(res.get(CQ_ANCHOR)));
				resRow.setCraetedAt(Bytes.toString(res.get(CQ_DT_REG)));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
				
		return resRow;
	}
	
	
	public String getAchorText(String siteId, String url) {
		try {
			return this.get(siteId + "_" + url, CF_MAIN, CQ_ANCHOR);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected String getTableName() {
		return TN_CRAWL ;
	}
	
	public static void main(String ... v) {
		System.out.println("Test .. " + System.currentTimeMillis());
		
		Connection conn = null;
		try {
			Configuration config = HBaseConfiguration.create();
			config.set("hbase.master", "127.0.0.1");
			conn = ConnectionFactory.createConnection(config);
			
			CrawlTable tblCrawl = new CrawlTable(conn);
			tblCrawl.createTable("cr1");
			System.out.println("Table Creation Success.. ");
			
//			tblCrawl.insertCrawledUrl("2", "http://www.naver.com", "네이버 x NAVER" + System.currentTimeMillis());
//			System.out.println("1. Successfully Data Added ..");
//			
//			String anchorText = tblCrawl.getAchorText("1", "http://www.naver.com");
//			System.out.println("2. Anchor Text :" + anchorText);
//			
//			anchorText = tblCrawl.getAchorText("2", "http://www.naver.com");
//			System.out.println("3. Ex Anchor Text :" + anchorText);
//			
//			tblCrawl.close();
////			table.insertCrawledUrl(siteId, url, anchorText);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
