/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSJClient.java
 */
package com.elifes.hsj.client;

import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.elifes.hsj.IDBConfigLoader;
import com.elifes.hsj.client.netty.HSClientPipelineFactory;
import com.elifes.hsj.impl.DefaultDBConfigLoader;
import com.elifes.hsj.model.CompareOperator;
import com.elifes.hsj.model.DBConfig;
import com.elifes.hsj.packet.AbstractPacket;
import com.elifes.hsj.packet.IPacket;
import com.elifes.hsj.packet.InsertPacket;
import com.elifes.hsj.packet.OpenIndexPacket;
import com.elifes.hsj.util.IDGenerator;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午12:10:15
 *
 */
public class HSJClient {
	private static Logger logger = Logger.getLogger(HSJClient.class);
	private Map<String, AbstractPacket> packets = new HashMap<String, AbstractPacket>(8);
	private ClientBootstrap bootstrap;
	private ChannelFuture future;
	private IDBConfigLoader dbConfigLoader;
	
	private ConcurrentMap<String, Channel> connectionPool = new ConcurrentHashMap<String, Channel>();
	
	private static HSJClient self = new HSJClient();

	public HSJClient(){
		//packets.put(key, value);
		bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new HSClientPipelineFactory());
		bootstrap.setOption("tcpNoDelay", true);
		
		dbConfigLoader = new DefaultDBConfigLoader();
		
	}
	
	public static HSJClient getInstance(){
		return self;
	}
	
	
//	public void init() {
//		// Configure the client.
//
//
//		// Configure the pipeline factory.
//		bootstrap.setPipelineFactory(new HSClientPipelineFactory());
//
//		// Start the connection attempt.
//		
//	}
	
	public String requsetOpenIndex(String dbName, String tblName,
			String indexName, List<String> columnNames) {
		DBConfig dbConfig = dbConfigLoader.load();
		future = bootstrap.connect(new InetSocketAddress(dbConfig.getIp(),
				dbConfig.getPort()));
		
		Channel channel = getChannel();
		if(!future.isSuccess()){
			//logger.error("connect error" +  future.getCause().getMessage());
			throw new RuntimeException("open table error, detail is:" + future.getCause().getMessage());
		}
		String indexId = String.valueOf(IDGenerator.nextId());
		IPacket openIndex = new  OpenIndexPacket(indexId, dbName, tblName,
				indexName, columnNames);
		
		//ChannelFuture lastWriteFuture = channel.write(openIndex.encode());
		
		ChannelFuture lastWriteFuture = channel.write("P	1	test	user	PRIMARY last_name,first_name" + "\r\n");
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lastWriteFuture.awaitUninterruptibly(20, TimeUnit.SECONDS);
		
		lastWriteFuture.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				future.getCause();
				
			}
		});
		
		boolean isTimeout = lastWriteFuture.awaitUninterruptibly(2, TimeUnit.SECONDS);
		if(!isTimeout){
			//throw new TimeoutException("open table timeout!");
		}
		if(!lastWriteFuture.isSuccess()){
			throw new RuntimeException("open table error, detail is:" + lastWriteFuture.getCause().getMessage());
		}
		connectionPool.put(indexId, channel);
		return indexId;
	}

	public void requsetAuth(String authType, String authKey) {
		
		
	}

	public void requsetInsert(String indexId, List<String> values) {
		Channel channel = connectionPool.get(indexId);
		if(!future.isSuccess()){
			//logger.error("connect error" +  future.getCause().getMessage());
			throw new RuntimeException("open table error, detail is:" + future.getCause().getMessage());
		}
		IPacket insertPacket = new  InsertPacket(indexId, values);
		channel.write(insertPacket.encode());
	}

	public ResultSet requsetFind(String id, CompareOperator operator,
			String[] filterValues, int limit, int offset,
			String[] filterColumnNames) {
		
		return null;
	}

	private Channel getChannel(){
		boolean notTimeout = future.awaitUninterruptibly(2, TimeUnit.SECONDS);
		if(notTimeout){
			return future.awaitUninterruptibly().getChannel();
		}
		
		return null;
	}
}
