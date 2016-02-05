package com.yg.webshow.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * RowKey design : [site_id]_[inversed_timestamp]
 * Column needed : [anchor_text] [url] [regitered_at] [doc_title] [contents] [r_cnt] [rep_n]
 * 
 * @author jeff.yg.kim@gmail.com
 *
 */
public class NewsSummaryTable extends AbstractTable {
	
	public static final String TBL_NAME = "newsSummary";
	public static final byte[] CF_MAIN = Bytes.toBytes("cr1");
	
	public static final byte[] CQ_ANCHOR_TEXT = Bytes.toBytes("a_tx");
	public static final byte[] CQ_REG_AT = Bytes.toBytes("reg");
	public static final byte[] CQ_DOC_TITLE = Bytes.toBytes("d_tl");
	public static final byte[] CQ_CONTENTS = Bytes.toBytes("cont");
	public static final byte[] CQ_REPL_CNT = Bytes.toBytes("rcnt");
	public static final String CQ_REPL_START = "rpl_";
	
	public NewsSummaryTable() {
		super();
	}
	
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TBL_NAME;
	}
	
	public List<NewsSummaryRow> getNewsSummary(String seedId, int startIndex, int offset) {
		return null;
	}
	
	
	public String putNewData(NewsSummaryRow nsRow) {
		String key = nsRow.getSeedId() + "_" + this.getReverseTimestamp();
		
		Map<byte[], byte[]> colums = new HashMap<byte[], byte[]>();
		try {
			colums.put(CQ_ANCHOR_TEXT, Bytes.toBytes(nsRow.getAnchorText()));
			colums.put(CQ_DOC_TITLE, Bytes.toBytes(nsRow.getDocTitle()));
			colums.put(CQ_CONTENTS, Bytes.toBytes(nsRow.getContents()));
			
			if(nsRow.getExtra() != null) {
				Map<String, String> mapExtra = nsRow.getExtra();
				colums.put(CQ_REPL_CNT, Bytes.toBytes((int)mapExtra.size()));				
				
				int i = 0;
				for(String exKey : mapExtra.keySet()) {
					colums.put(Bytes.toBytes(CQ_REPL_START + i++), Bytes.toBytes(mapExtra.get(exKey)));
				}
			} else {
				colums.put(CQ_REPL_CNT, Bytes.toBytes((int)1));
			}	
			
			this.put(key, CF_MAIN, colums);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return key;
	}
	
	/**
	 * Add the latest value as first row to table
	 * @return
	 */
	public String getReverseTimestamp() {
		return Long.toString(Long.MAX_VALUE - System.currentTimeMillis());
	}
	
	public static void main(String ... v) {
		NewsSummaryTable newsSum = new NewsSummaryTable();
		NewsSummaryRow nsRow = new NewsSummaryRow();
		nsRow.setSeedId("999999");
		nsRow.setAnchorText("3This is a message for test!");
		nsRow.setDocTitle("3[TITLE] The winter is comming");
		nsRow.setContents("3ABCD 1234 @#");
		
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("rep1", "3This messsage is goooood !");
		extra.put("rep2", "3ahehe1");
//		extra.put("rep3", "2I am no.1 ~");
		
		nsRow.setExtra(extra);
		
		String putResKey = newsSum.putNewData(nsRow) ;
		System.out.println("PUT Result :" + putResKey);
		
//		System.out.println("-->" + newsSum.getReverseTimestamp());
//		try { Thread.sleep(1000); } catch (InterruptedException e) {}
//		
//		System.out.println("-->" + newsSum.getReverseTimestamp());
//		try { Thread.sleep(1000); } catch (InterruptedException e) {}
//		
//		System.out.println("-->" + newsSum.getReverseTimestamp());
//		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		
//		try {
//			newsSum.createTable(Bytes.toString(CF_MAIN));
//			System.out.println("Successfully Table created ..");
//			
//			newsSum.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
