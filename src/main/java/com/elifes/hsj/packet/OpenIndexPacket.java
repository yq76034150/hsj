/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: OpenIndexPacket.java
 */
package com.elifes.hsj.packet;

import java.util.List;

import com.elifes.hsj.util.CollectionUtil;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:10:09
 *
 */
public class OpenIndexPacket extends AbstractPacket{
	private final String dbName;
	private final String tblName;
	private final String indexName;
	private final List<String> columnNames;
	//private final List<String> filterFieldList;
	
	public OpenIndexPacket(String indexId, String db, String tableName, String indexName,
			List<String> columnNames) {
		this.indexId = indexId;
		this.dbName = db;
		this.tblName = tableName;
		this.indexName = indexName;
		this.columnNames = columnNames;
		//this.filterFieldList = filterFieldList;
	}

	public String encode() {
		StringBuffer requestMsg = new StringBuffer();
		// header
		this.writeToken(requestMsg, OPERATOR_OPEN_INDEX);
		this.writeTokenSeparator(requestMsg);
		// id
		this.writeToken(requestMsg, this.indexId);
		this.writeTokenSeparator(requestMsg);
		// db name
		this.writeToken(requestMsg, this.dbName);
		this.writeTokenSeparator(requestMsg);
		// tableName
		this.writeToken(requestMsg, this.tblName);
		this.writeTokenSeparator(requestMsg);
		// indexName
		this.writeToken(requestMsg, this.indexName);
		this.writeTokenSeparator(requestMsg);
		// field list
		this.writeToken(requestMsg, CollectionUtil.join(columnNames));

		// filter field list
//		if (filterFieldList.size() != 0) {
//			this.writeTokenSeparator(requestMsg);
//			this.writeToken(requestMsg, CollectionUtil.join(filterFieldList));
//			this.writeCommandTerminate(requestMsg);
//		} else {
//			this.writeCommandTerminate(requestMsg);
//
//		}
		
		return requestMsg.toString();
	}

}
