package com.ygsoft.webshow.data ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.ygsoft.SysConf;

/**
 * RowKey : [site_id]_[reversed_post_no]
 * CQ : timestamp
 * 		url
 * 		anchor_title
 * 		contents
 * 		cont_title
 * 		crawl_status
 * 		author
 * 		doc_ts
 * 		media_n
 */
public class RtCrawlTable extends AbstractTableEx {
	private static final byte[] TABLE_NAME = Bytes.toBytes("rtCrawl"); 
	private static final byte[] CF = Bytes.toBytes("cwl");
	
	private static final byte[] CQ_SITE_ID = Bytes.toBytes("sid");
	private static final byte[] CQ_CONTENTS = Bytes.toBytes("cont");
	private static final byte[] CQ_HTML_CONTENTS = Bytes.toBytes("hcnt");
	private static final byte[] CQ_ANCHOR_TITLE = Bytes.toBytes("alt");
	private static final byte[] CQ_DOC_TITLE = Bytes.toBytes("dtl");
	private static final byte[] CQ_CONT_TITLE = Bytes.toBytes("ctl");
	private static final byte[] CQ_AUTHOR = Bytes.toBytes("ath");
	private static final byte[] CQ_DOC_TS = Bytes.toBytes("dts");
	private static final byte[] CQ_STATUS = Bytes.toBytes("sts");
	private static final byte[] CQ_URL = Bytes.toBytes("url");
	private static final byte[] CQ_MEDIA_COUNT = Bytes.toBytes("mdc");
	
	private static final String PREFIX_MEDIA = "med_";
	
	public static final String VAL_STATUS_INIT = "INIT";
	public static final String VAL_STATUS_EXTDATA = "MERGED";
	
	public RtCrawlTable(Connection conn) {
		super(conn);
	}
	
	private byte[] createRowKey(String siteId, int postNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(siteId).append("_");
		sb.append(Integer.MAX_VALUE - postNo);
		
		return Bytes.toBytes(sb.toString()) ;
	}
	
	public CrawlDataExBo getCrawlData(String siteId, int postNo) {
		CrawlDataExBo cdb = new CrawlDataExBo() ;
		cdb.setSiteId(siteId);
		cdb.setPostId(String.valueOf(postNo));
		
		Get get = new Get(this.createRowKey(siteId, postNo));
		Result rs = super.getData(get) ;
		
		while(rs.advance()) {
			Cell cell = rs.current();
			
			if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_SITE_ID)) {
				cdb.setSiteId(new String(CellUtil.cloneValue(cell)));
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_CONTENTS)) {
				cdb.setFilteredContents(new String(CellUtil.cloneValue(cell)));
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_ANCHOR_TITLE)) {
				cdb.setAnchorTitle(new String(CellUtil.cloneValue(cell)));;
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_DOC_TITLE)) {
				cdb.setDocTitle(new String(CellUtil.cloneValue(cell)));;
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_AUTHOR)) {
				cdb.setAuthor(new String(CellUtil.cloneValue(cell)));;
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_DOC_TS)) {
				cdb.setDocTs(Bytes.toLong(CellUtil.cloneValue(cell)));
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_STATUS)) {
				cdb.setStatus(new String(CellUtil.cloneValue(cell)));;
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_URL)) {
				cdb.setUrl(new String(CellUtil.cloneValue(cell)));
			} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_HTML_CONTENTS)) {
				cdb.setHtmlContents(new String(CellUtil.cloneValue(cell)));
			}
		}
				
		return cdb ;
	}
	
	public void addInitData(String siteId, int postNo, String author, String anchorTitle, long docTs, String url) {
		
		Put put = new Put(this.createRowKey(siteId, postNo));
		put.addColumn(CF, CQ_AUTHOR, Bytes.toBytes(author));
		put.addColumn(CF, CQ_ANCHOR_TITLE, Bytes.toBytes(anchorTitle));
		put.addColumn(CF, CQ_URL, Bytes.toBytes(url));
		put.addColumn(CF, CQ_DOC_TS, Bytes.toBytes(docTs));
		put.addColumn(CF, CQ_STATUS, Bytes.toBytes(VAL_STATUS_INIT));
		
		super.putData(put);
	}
	
	public void updateExtData(String siteId, int postNo, String status, String docTitle, 
			String contents, String htmlContents, List<String> lstMedia) {
		Put put = new Put(this.createRowKey(siteId, postNo));
		put.addColumn(CF, CQ_STATUS, Bytes.toBytes(VAL_STATUS_EXTDATA)) ;
		if(docTitle != null) put.addColumn(CF, CQ_DOC_TITLE, Bytes.toBytes(docTitle)) ;
		if(contents != null) put.addColumn(CF, CQ_CONTENTS, Bytes.toBytes(contents));
		if(htmlContents != null) put.addColumn(CF, CQ_HTML_CONTENTS, Bytes.toBytes(htmlContents));
		
		if(lstMedia != null && lstMedia.size() > 0) {
			put.addColumn(CF, CQ_MEDIA_COUNT, Bytes.toBytes(lstMedia.size()));
			int i = 0;
			for(String media : lstMedia) {
				put.addColumn(CF, Bytes.toBytes(PREFIX_MEDIA + i), Bytes.toBytes(media));
			}
		}
		
		super.putData(put);
	}
	
	
	public List<CrawlDataExBo> getLatest(int topN, String status) {
		ArrayList<CrawlDataExBo> res = new ArrayList<CrawlDataExBo>();
		
		Scan scan = new Scan();
		scan.addFamily(CF);
		FilterList fList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		if(status != null) {
			fList.addFilter(new SingleColumnValueFilter(CF,
					CQ_STATUS, CompareOp.EQUAL, Bytes.toBytes(status)));
		}
		fList.addFilter(new PageFilter(topN));
		scan.setFilter(fList) ;
//		scan.setFilter(new PageFilter(topN));
		
		ResultScanner rsc = super.scanData(scan) ;
		Result rs = null;
		
		try {
			CrawlDataExBo cdb = null ;
			while((rs = rsc.next()) != null) {
				CellScanner cs = rs.cellScanner();
				cdb = new CrawlDataExBo() ;

				// Process RowKey
				String rowKey = new String(rs.getRow()) ;
				String[] token = rowKey.split("_");
				if(token.length == 2) {
					int postId = Integer.MAX_VALUE - Integer.parseInt(token[1]);
					cdb.setPostId(String.valueOf(postId));
				}
				
				// Process Cells
				while(rs.advance()) {
					Cell cell = rs.current();
					
					if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_SITE_ID)) {
						cdb.setSiteId(new String(CellUtil.cloneValue(cell)));
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_CONTENTS)) {
						cdb.setFilteredContents(new String(CellUtil.cloneValue(cell)));
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_ANCHOR_TITLE)) {
						cdb.setAnchorTitle(new String(CellUtil.cloneValue(cell)));;
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_DOC_TITLE)) {
						cdb.setDocTitle(new String(CellUtil.cloneValue(cell)));;
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_AUTHOR)) {
						cdb.setAuthor(new String(CellUtil.cloneValue(cell)));;
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_DOC_TS)) {
						cdb.setDocTs(Bytes.toLong(CellUtil.cloneValue(cell)));
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_STATUS)) {
						cdb.setStatus(new String(CellUtil.cloneValue(cell)));;
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_URL)) {
						cdb.setUrl(new String(CellUtil.cloneValue(cell)));
					} else if(Arrays.equals(CellUtil.cloneQualifier(cell), CQ_HTML_CONTENTS)) {
						cdb.setHtmlContents(new String(CellUtil.cloneValue(cell)));
					}
				}
				res.add(cdb) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res ;
	}
	
	@Override
	public byte[] getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
	
	public static void main(String ... v) {
		System.out.println("Run ...");
		RtCrawlTable test = new RtCrawlTable(SysConf.createNewHbaseConn());
		byte[] createdRowKey = test.createRowKey("1", 1399);
		
		System.out.println("Key --> " + new String(createdRowKey));
		
//		test.addInitData("19", 223, "tabaco", "Fire Potato~", System.currentTimeMillis(), "http://www.naver.com");
		
		int i = 0;
		List<CrawlDataExBo> latest = test.getLatest(1, RtCrawlTable.VAL_STATUS_INIT);
		for(CrawlDataBo cdb : latest) {
			System.out.println(i++ + " -> " + cdb);
		}
		
		System.out.println("---------------");
		CrawlDataExBo crawlData = test.getCrawlData("clien.park", 10714694) ;
		System.out.println("GetData -->" + crawlData);
	}
}
