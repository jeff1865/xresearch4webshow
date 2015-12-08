package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class CrawlTable extends AbstractTable {
	public static final String TN_CRAWL = "crawl1449569734683";
	public static final String CF_MAIN = "cr1";
	public static final String CN_KEY = "cf_m";
	public static final String CN_DT_REG = "regdt";
	public static final String CQ_ANCHOR = "ac_txt";
	
	private Connection conn = null;
	
	public CrawlTable(Connection conn) {
		super(conn);
	}
		
	public void insertCrawledUrl(String siteId, String url, String anchorText) {
		try {
			this.put(siteId + "_" + url, CF_MAIN, CQ_ANCHOR, anchorText);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
//			tblCrawl.createTable("cr1", "cr2");
//			System.out.println("Table Creation Success.. ");
			
			tblCrawl.insertCrawledUrl("2", "http://www.naver.com", "네이버 NAVER" + System.currentTimeMillis());
			System.out.println("1. Successfully Data Added ..");
			
			String anchorText = tblCrawl.getAchorText("1", "http://www.naver.com");
			System.out.println("2. Anchor Text :" + anchorText);
			
			anchorText = tblCrawl.getAchorText("2", "http://www.naver.com");
			System.out.println("3. Ex Anchor Text :" + anchorText);
			
			tblCrawl.close();
//			table.insertCrawledUrl(siteId, url, anchorText);
			
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
