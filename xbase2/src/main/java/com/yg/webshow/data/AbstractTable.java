package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public abstract class AbstractTable {
	private static Logger log = Logger.getLogger(AbstractTable.class);
	
	protected Connection conn = null;
	
	public AbstractTable(Connection conn) {
		this.conn = conn;
	}
	
	public void close() {
		try {
			this.conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Table getTable() {
		try {
			return this.conn.getTable(TableName.valueOf(this.getTableName()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	protected void put(String key, String cf, String colName, String value) throws IOException {
		Table table = this.getTable();
		
		Put put = new Put(Bytes.toBytes(key));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName), Bytes.toBytes(value));
		
		table.put(put);
		table.close();
	}
	
	protected String get(String rowKey, String cf, String colName) throws IOException {
		Table table = this.getTable();
		
		Get get = new Get(Bytes.toBytes(rowKey));
		
		Result result = table.get(get);
		byte[] value = result.getValue(Bytes.toBytes(cf), Bytes.toBytes(colName));
				
		return Bytes.toString(value);
	}
	
	public void createTable(String ... cfs) throws IOException {
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(this.getTableName()));
		for(String cf : cfs) {
			tableDesc.addFamily(new HColumnDescriptor(cf));
		}
		
		Admin admin = conn.getAdmin();
		admin.createTable(tableDesc);
		log.info("Successfull table created : " + this.getTableName());
	}
	
	protected abstract String getTableName();
}
