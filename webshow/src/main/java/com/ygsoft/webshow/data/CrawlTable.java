package com.ygsoft.webshow.data;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;

import com.ygsoft.SysConf;

/**
 * RowKey : [reversed_ts]_[site_id]_[doc_type]_[postId]
 * CQ : siteId 
 *      contents
 *      title
 *      docTitle
 *      author
 *      docTs
 *      media_n -> type:value
 *      status
 */
public class CrawlTable extends AbstractTableEx {
	private static final byte[] TABLE_NAME = Bytes.toBytes("slCrawl"); 
	private static final byte[] CF = Bytes.toBytes("cwl");
	
	private static final byte[] CQ_SITE_ID = Bytes.toBytes("sid");
	private static final byte[] CQ_CONTENTS = Bytes.toBytes("cont");
	private static final byte[] CQ_TITLE = Bytes.toBytes("tl");
	private static final byte[] CQ_DOC_TITLTE = Bytes.toBytes("dtl");
	private static final byte[] CQ_AUTHOR = Bytes.toBytes("ath");
	private static final byte[] CQ_DOC_TS = Bytes.toBytes("dts");
	private static final byte[] CQ_STATUS = Bytes.toBytes("sts");
	
	private static final String PREFIX_MEDIA = "med_";
	
	protected CrawlTable(Connection conn) {
		super(conn);
	}	
	
	public void addData(long ts) {
		;
	}
	
	private byte[] createRowKey(long ts, String siteId, String docType, String postId) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(Long.toString(Long.MAX_VALUE - ts)).append("_");
		sb.append(siteId).append("_");
		sb.append(docType).append("_");
		sb.append(postId);
		
		return Bytes.toBytes(sb.toString()) ;
	}
	
	@Override
	public byte[] getTableName() {
		return TABLE_NAME;
	}
	
	public static void main(String ... v) {
		System.out.println("Run System ..");
		
		CrawlTable test = new CrawlTable(SysConf.createNewHbaseConn()) ;
		
		String strKey = new String(test.createRowKey(System.currentTimeMillis(), "A101", "BBS", "1234"));
		System.out.println(">>>" + strKey);
		
		try { Thread.sleep(1000); } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		strKey = new String(test.createRowKey(System.currentTimeMillis(), "A101", "BBS", "1234"));
		System.out.println(">>>" + strKey);
	}

	
}
