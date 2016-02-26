package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.hbase.util.Bytes;

public class WrapperRuleTable extends AbstractTable {
	public static final String TBL_NAME = "wrapperRule";
	public static final byte[] CF_MAIN = Bytes.toBytes("cr1");
	public static final byte[] CQ_DOC_TITLE = Bytes.toBytes("tlt");
	public static final byte[] CQ_DOC_DATE = Bytes.toBytes("dt");
	public static final byte[] CQ_DOC_CONTENTS = Bytes.toBytes("cont");
	public static final byte[] CQ_DOC_REPLY = Bytes.toBytes("tlt");
	
	public WrapperRuleTable() {
		super();
	}
	
	@Override
	protected String getTableName() {
		return TBL_NAME;
	}
	
	public static void main(String ... v) {
		WrapperRuleTable tbl = new WrapperRuleTable();
		try {
			tbl.createTable(Bytes.toString(CF_MAIN));
			System.out.println("Successfully Table created ..");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
