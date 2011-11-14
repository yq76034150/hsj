/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSJManager.java
 */
package com.elifes.hsj;

import java.sql.ResultSet;
import java.util.List;

import com.elifes.hsj.model.CompareOperator;
import com.elifes.hsj.model.TableConfig;

/**
 * 描述：
 * 
 * @author yangqiang
 * @createtime 2011-11-12下午12:17:28
 * 
 */
public interface HSJManager {
	/**
	 * 
	 * 描述：初始化 与 hs的连接
	 * @param dbLookupStrategy 获取的连接信息的策略类
	 * @return 获取连接的标识
	 */
	public String init(IDBLookupStrategy dbLookupStrategy);
	/**
	 * 
	 * 描述：
	 * @param tableConfig
	 * @return
	 */
	public String openIndex(TableConfig tableConfig);
	
	public String openIndex(String dbName, String tblName, String indexName,
			List<String> columnNames);

//	public String openIndex(String indexId, String dbName, String tblName,
//			String indexName, List<String> columnNames);

	public void auth(String authType, String authKey);

	public void insert(String indexId, List<String> values);

	public ResultSet find(String indexId, CompareOperator operator,
			String[] filterValues, int limit, int offset,
			String[] filterColumnNames);



	public void insert(List<String> values);

	public ResultSet find(CompareOperator operator, String[] filterValues,
			int limit, int offset, String[] filterColumnNames);
}
