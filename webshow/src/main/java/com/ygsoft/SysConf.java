package com.ygsoft;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConf {
	
	public static String HBASE_QUORUM = "127.0.0.1";
	public static String HBASE_MASTER = "127.0.0.1";
	
//	public static String HBASE_QUORUM = null;
//	public static String HBASE_MASTER = null;
	
//	public static String HBASE_QUORUM = "172.21.224.64";
//	public static String HBASE_MASTER = "172.21.224.64";
		
//	private static HbaseConf hbaseConf = null;
	
	public SysConf() {
		;
	}
	
//	@Autowired
//	public void setHbaseConf(HbaseConf hbaseConf) {
//		SysConf.hbaseConf = hbaseConf ;
//		
//		HBASE_QUORUM = hbaseConf.getQuorum();
//		HBASE_MASTER = hbaseConf.getMaster();
//	}
		
	public static Connection createNewHbaseConn() {
		Configuration config = HBaseConfiguration.create();
		
		//LOCAL_DEV
		config.set("hbase.zookeeper.quorum", HBASE_QUORUM);
		config.set("hbase.master", HBASE_MASTER);
		
		//LIVE
//		config.set("hbase.zookeeper.quorum", "172.21.224.64");
//		config.set("hbase.master", "172.21.224.64");
		
		try {
			return ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
