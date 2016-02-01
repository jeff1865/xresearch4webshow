package com.yg.webshow.data;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public abstract class AbstractTable {
	private static Logger log = Logger.getLogger(AbstractTable.class);
	
	protected Connection conn = null;
	
	public AbstractTable() {
		EnvManager envMng = new EnvManager();
		this.conn = envMng.getNewConnection();
	}
	
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
	
	protected ResultScanner scanData(String start, String end) throws IOException {
		Table table = this.getTable();
		
		Scan scan = new Scan(Bytes.toBytes(start), Bytes.toBytes(end));
		ResultScanner rs = table.getScanner(scan);
		
		table.close();
		return rs;
				
	} 
	
	protected void putString(String key, String cf, Map<String, String> values) throws IOException {
		Table table = this.getTable();
		Put put = new Put(Bytes.toBytes(key));
		
		for(String cq : values.keySet()) {
			put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cq), Bytes.toBytes(values.get(cq)));
		}
		
		table.put(put);
		table.close();
	}
	
	//TODO need to delete
	protected void put(String key, String cf, Map<byte[], byte[]> values) throws IOException {
		Table table = this.getTable();
		Put put = new Put(Bytes.toBytes(key));
		
		for(byte[] cq : values.keySet()) {
			put.addColumn(Bytes.toBytes(cf), cq, values.get(cq));
		}
		
		table.put(put);
		table.close();
	}
	
	protected void put(String key, String cf, String colName, String value) throws IOException {
		Table table = this.getTable();
		
		Put put = new Put(Bytes.toBytes(key));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName), Bytes.toBytes(value));
		
		table.put(put);
		table.close();
	}
	
	protected Map<String, byte[]> get(String rowKey, String cf, String ... cols) throws IOException {
		LinkedHashMap<String, byte[]> resTable = new LinkedHashMap<String, byte[]>();
		
		Table table = this.getTable();
		Get get = new Get(Bytes.toBytes(rowKey));
		Result result = table.get(get);
		if(result.size() <= 0) {
			table.close();
			return null;
		}
		
		for(String colName : cols) {
			byte[] value = result.getValue(Bytes.toBytes(cf), Bytes.toBytes(colName));
			resTable.put(colName, value);
		}
		
		table.close();
		return resTable;
	}
	
	protected String get(String rowKey, String cf, String colName) throws IOException {
		Table table = this.getTable();
		
		Get get = new Get(Bytes.toBytes(rowKey));
		
		Result result = table.get(get);
		byte[] value = result.getValue(Bytes.toBytes(cf), Bytes.toBytes(colName));
		
		table.close();		
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
