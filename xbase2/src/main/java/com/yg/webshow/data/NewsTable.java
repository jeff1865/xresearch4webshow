package com.yg.webshow.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.yg.webshow.util.DateUtil;
import com.yg.webshow.util.TextUtil;

//TODO need to change scheme ..
public class NewsTable extends AbstractTable {
	public static final String TABLE_NAME = "newslist";
	public static final String CF_MAIN = "cr1";
	public static final String CF_SUB1 = "cr2";
	public static final String CQ_ANCHOR = "anc";
	public static final String CQ_LINK = "lnk";
	public static final String CQ_SITE_ID = "sid";
	public static final String CQ_NEWS_STATUS = "sts";
	
	public static final String VAL_NEWS_STATUS_INIT = "init";
	public static final String VAL_NEWS_STATUS_RT_COMPLETED = "completed";
	public static final String VAL_NEWS_STATUS_MR_COMPLETED = "mrCompleted";
	public static final String VAL_NEWS_STATUS_ERROR = "error";
	
	/*
	 * Key = [siteid]_[timestamp] | 
	 */
	
	public NewsTable(Connection conn) {
		super(conn);
	}
	
	public void insertNews(String siteId, String url, String anchor) {
		String fSiteId = TextUtil.getFixedLengthText(siteId);
		String tsKey = DateUtil.getCurrentTsKey();
		String rowKey = fSiteId + "_" + tsKey;
		
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put(CQ_ANCHOR, anchor);
		data.put(CQ_LINK, url);
		data.put(CQ_SITE_ID, siteId);
		data.put(CQ_NEWS_STATUS, VAL_NEWS_STATUS_INIT);
		
		try {
			this.putString(rowKey, CF_MAIN, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<NewsRow> getNews(int max, String start, String end) {
		ArrayList<NewsRow> lstNewsRow = new ArrayList<NewsRow> ();
		
		ResultScanner scanData = null;
		try {
			scanData = this.scanData(start, end);
			NewsRow newsRow = null;
			int i = 0;
			for(Result r : scanData) {
				if(++i > max) break;
				String rowKey = new String(r.getRow());
				String[] key = rowKey.split("_");
				
				newsRow = new NewsRow() ;
				newsRow.setSiteId(key[0]);
				newsRow.setRegDate(key[1]);
				if(r.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_ANCHOR)) != null)
					newsRow.setAnchorText(new String(r.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_ANCHOR))));
				if(r.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_LINK)) != null)
					newsRow.setLink(new String(r.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_LINK))));
				
				lstNewsRow.add(newsRow);
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanData.close();
		}
		
		return lstNewsRow ;
	}
	
//	@Deprecated
//	public void testScan() {
//		try {
//			ResultScanner rs = this.scanData("0000004", "0000005");
//			int i = 0;
//			for(Result r : rs) {
//				System.out.println(i++ + ".>>>" + new String(r.getRow()));
//				System.out.println("-->" + new String(r.getValue(Bytes.toBytes(CF_MAIN), Bytes.toBytes(CQ_ANCHOR))));
//			}
//			rs.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			
//		}
//	}
		
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
	
}
