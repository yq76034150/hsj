/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: DefaultDBLookupStrategy.java
 */
package com.elifes.hsj.client;

import com.elifes.hsj.IDBLookupStrategy;
import com.elifes.hsj.model.TableConfig;

/**
 * 描述：默认实现，直接指定数据库信息
 * @author yangqiang
 * @createtime 2011-11-12下午1:55:19
 *
 */
public class DefaultDBLookupStrategy implements IDBLookupStrategy {
	private TableConfig tableConfig;
	
	public DefaultDBLookupStrategy(TableConfig tableConfig){
		this.tableConfig = tableConfig;
	}

	/* (non-Javadoc)
	 * @see com.elifes.hsj.IDBLookupStrategy#lookup(java.lang.String)
	 */
	public TableConfig lookup(String lookupKey) {
		// TODO Auto-generated method stub
		return tableConfig;
	}

//	public TableConfig getTableConfig() {
//		return tableConfig;
//	}
//
//	public void setTableConfig(TableConfig tableConfig) {
//		this.tableConfig = tableConfig;
//	}
	
	

}
