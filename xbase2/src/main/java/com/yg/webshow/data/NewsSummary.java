package com.yg.webshow.data;

/**
 * RowKey design : [site_id]_[inversed_timestamp]
 * Column needed : [anchor_text] [url] [regitered_at] [doc_title] [contents] [rep_n]
 * 
 * @author jeff.yg.kim@gmail.com
 *
 */
public class NewsSummary extends AbstractTable {
	
	public static final String TBL_NAME = "newsSummary";
	public static final String CF_MAIN = "cr1";
	public static final String CQ_DT_REG = "reg";
	public static final String CQ_ANCHOR = "ac_txt";
	
	public NewsSummary() {
		;
	}
	
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TBL_NAME;
	}
	
}
