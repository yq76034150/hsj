/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: DefaultHSJManager.java
 */
package com.elifes.hsj.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.elifes.hsj.HSJManager;
import com.elifes.hsj.IDBConfigLoader;
import com.elifes.hsj.IDBLookupStrategy;
import com.elifes.hsj.client.HSJClient;
import com.elifes.hsj.client.HSJClientFactory;
import com.elifes.hsj.model.CompareOperator;
import com.elifes.hsj.model.DBConfig;
import com.elifes.hsj.model.TableConfig;
import com.elifes.hsj.util.Constants;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午12:40:13
 *
 */
public class DefaultHSJManager implements HSJManager {
	private static final Logger logger = Logger.getLogger(DefaultHSJManager.class);
	
	private static DefaultHSJManager self = new DefaultHSJManager();
	
	private ConcurrentMap<String, Channel> connectionMap = new ConcurrentHashMap<String, Channel>();
	
	private ConcurrentMap<TableConfig, String> indexIdMap = new ConcurrentHashMap<TableConfig, String>();
	//ThreadLocal<OpenIndexContext> ctx;

	//private IDBLookupStrategy dbLookupStrategy;
	private String encoding = Constants.DEFAULT_ENCODING;
	private TableConfig tableConfig;
	
//	static{
//		init();
//	}
	
	public static DefaultHSJManager getInstance(){
		return self;
	}

	public String init(IDBLookupStrategy dbLookupStrategy) {
		tableConfig = dbLookupStrategy.lookup(null);
		String indexId = null;
		
		if(!indexIdMap.containsKey(tableConfig)){
			indexId = openIndex(tableConfig);
			indexIdMap.putIfAbsent(tableConfig, indexId);
		}
		return indexId;
	}
	
	private HSJClient getClient(){
		HSJClientFactory hsjClientFactory = HSJClientFactory.getInstance();
		hsjClientFactory.setEncoding(encoding);
		hsjClientFactory.setTableConfig(tableConfig);
		return hsjClientFactory.createClient(tableConfig.getDbConfig().getIp(), tableConfig.getDbConfig().getPort());
	}

	public String openIndex(TableConfig tableConfig) {
		return openIndex(
				tableConfig.getDbConfig().getDbName(),
				tableConfig.getTblName(), tableConfig.getIndexName(),
				tableConfig.getColumnNames());
	}

	public String openIndex(String dbName, String tblName, String indexName,
			List<String> columnNames) {
		return getClient().requsetOpenIndex(dbName, tblName, indexName, columnNames);
	}
	/* (non-Javadoc)
	 * @see com.elifes.hsj.HSJManager#openIndex(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
//	public String openIndex(String indexId, String dbName, String tblName,
//			String indexName, List<String> columnNames) {
//		
//		return getClient().requsetOpenIndex(indexId, dbName, tblName, indexName, columnNames);
//	}

	/* (non-Javadoc)
	 * @see com.elifes.hsj.HSJManager#auth(java.lang.String, java.lang.String)
	 */
	public void auth(String authType, String authKey) {
		getClient().requsetAuth(authType, authKey);
	}

	/* (non-Javadoc)
	 * @see com.elifes.hsj.HSJManager#insert(java.lang.String, java.util.List)
	 */
	public void insert(String indexId, List<String> values) {
		getClient().requsetInsert(indexId, values);
	}

	/* (non-Javadoc)
	 * @see com.elifes.hsj.HSJManager#find(java.lang.String, com.elifes.hsj.model.CompareOperator, java.lang.String[], int, int, java.lang.String[])
	 */
	public ResultSet find(String id, CompareOperator operator,
			String[] filterValues, int limit, int offset,
			String[] filterColumnNames) {
		return getClient().requsetFind(id, operator, filterValues, limit, offset, filterColumnNames);
	}

	public void insert(List<String> values) {
		// TODO Auto-generated method stub
		
	}

	public ResultSet find(CompareOperator operator, String[] filterValues,
			int limit, int offset, String[] filterColumnNames) {
		// TODO Auto-generated method stub
		return null;
	}


}
