/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: TableConfig.java
 */
package com.elifes.hsj.model;

import java.util.List;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午1:32:33
 *
 */
public class TableConfig {
	private DBConfig dbConfig;
	private String tblName;
	private String indexName = "PRIMARY";
	private List<String> columnNames;
	private List<String> filterColumnNames;
	public DBConfig getDbConfig() {
		return dbConfig;
	}
	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<String> getFilterColumnNames() {
		return filterColumnNames;
	}
	public void setFilterColumnNames(List<String> filterColumnNames) {
		this.filterColumnNames = filterColumnNames;
	}
	
}
