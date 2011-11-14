/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: PacketFactory.java
 */
package com.elifes.hsj.packet;

import java.util.List;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:59:45
 *
 */
public class PacketFactory {
	private static PacketFactory self = new PacketFactory();
	
	public static PacketFactory getInstance(){
		return self;
	}
	
	public IPacket createAuthPacket(String authKey){
		IPacket authPacket = new AuthPacket(authKey);
		return authPacket;
	}
	
	public IPacket createOpenIndexPacket(String indexId, String dbName, String tblName, String indexName, List<String> columnNames){
		IPacket openIndexPacket = new OpenIndexPacket(indexId, dbName, tblName, indexName, columnNames);
		return openIndexPacket;
	}
	
	public IPacket createInsertPacket(String indexId, List<String> values){
		IPacket insertPacket = new InsertPacket(indexId, values);
		return insertPacket;
	}
}
