package com.yg.webshow.data;

public class SeedInfoTable extends AbstractTable {
	private final String tableName = "seeds";
	
	public SeedInfoTable() {
		super();
	}
	
	@Override
	protected String getTableName() {
		return this.tableName;
	}
	
	public static void main(String ... v) {
		System.out.println("Activated ...");
	}
}
