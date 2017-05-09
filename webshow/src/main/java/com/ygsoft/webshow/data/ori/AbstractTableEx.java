package com.ygsoft.webshow.data;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import com.ygsoft.SysConf;



public abstract class AbstractTableEx {
	protected Connection conn = null ;
	
	protected AbstractTableEx(Connection conn) {
		this.conn = conn ;
	}
	
	protected void putData(Put put) {
		Table table = null ;
		try {
			if(this.conn == null || this.conn.isClosed()) {
				this.conn = getConnection();
			}
			
			table = this.conn.getTable(TableName.valueOf(getTableName()));
			table.put(put);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void putData(List<Put> put) {
		Table table = null ;
		try {
			if(this.conn == null || this.conn.isClosed()) {
				this.conn = getConnection();
			}
			
			table = this.conn.getTable(TableName.valueOf(getTableName()));
			table.put(put);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO
	protected Result getData(Get get) {
		Table table = null ;
		try {
			if(this.conn == null || this.conn.isClosed()) {
				this.conn = getConnection();
			}
			
			table = this.conn.getTable(TableName.valueOf(getTableName()));
			Result result = table.get(get) ;
			
			return result ;			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null ;
	}
	
	protected ResultScanner scanData(Scan scan) {
		Table table = null ;
		try {
			if(this.conn == null || this.conn.isClosed()) {
				this.conn = getConnection();
			}
			
			table = this.conn.getTable(TableName.valueOf(getTableName()));
			ResultScanner scanner = table.getScanner(scan);
			
			return scanner ;			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null ;
	}
	
	public static Connection getConnection() {
		return SysConf.createNewHbaseConn();
	}
	
	public abstract byte[] getTableName() ;
}
