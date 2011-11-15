/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSJClient.java
 */
package com.elifes.hsj.client;

import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.elifes.hsj.IDBConfigLoader;
import com.elifes.hsj.client.netty.HSClientPipelineFactory;
import com.elifes.hsj.exception.HSJException;
import com.elifes.hsj.impl.DefaultDBConfigLoader;
import com.elifes.hsj.model.CompareOperator;
import com.elifes.hsj.model.DBConfig;
import com.elifes.hsj.packet.AbstractPacket;
import com.elifes.hsj.packet.IPacket;
import com.elifes.hsj.packet.PacketFactory;
import com.elifes.hsj.util.Constants;
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
	
	private ConcurrentHashMap<Integer,ResponseFuture> pendingRequest = new ConcurrentHashMap<Integer,ResponseFuture>();
	//private ConcurrentMap<String, CopyOnWriteArrayList<Channel>> connectionPool = new ConcurrentHashMap<String, CopyOnWriteArrayList<Channel>>();
	
	private List<String> indexIdList = new ArrayList<String>();
	private List<Channel> channelList = new ArrayList<Channel>();
	private BlockingQueue<Channel> connectionPool = new LinkedBlockingQueue<Channel>();
	
	private static HSJClient self = new HSJClient();

	public HSJClient(){
		//packets.put(key, value);
		bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new HSClientPipelineFactory(this));
		bootstrap.setOption("tcpNoDelay", true);
		
		dbConfigLoader = new DefaultDBConfigLoader();
		DBConfig dbConfig = dbConfigLoader.load();
		future = bootstrap.connect(new InetSocketAddress(dbConfig.getIp(),
				dbConfig.getPort()));
		
		//初始化 就建立多个indexId 和 channel 
		initConnectionPool();
	}
	
	public static HSJClient getInstance(){
		return self;
	}
	
	private void initConnectionPool() {
		for(int i = 0; i < 2; i++){
			Channel channel = getChannel();
			connectionPool.offer(channel);
		}
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
			String indexName, List<String> columnNames) throws HSJException {
		Channel channel = getChannelFromPool();
		
		if(!future.isSuccess()){
			//logger.error("connect error" +  future.getCause().getMessage());
			throw new RuntimeException("open table error, detail is:" + future.getCause().getMessage());
		}
		String indexId = String.valueOf(IDGenerator.nextId());
		
		//PacketFactory.getInstance().createAuthPacket(dbConfigLoader.load().getAuthKey());
		if(requsetAuth(null, dbConfigLoader.load().getAuthKey())){
			IPacket openIndexPacket = PacketFactory.getInstance().createOpenIndexPacket(indexId, dbName, tblName, indexName, columnNames);
			//ChannelFuture lastWriteFuture = channel.write(openIndexPacket.encode());
			
			//ChannelFuture lastWriteFuture = channel.write("P	1	test	user	PRIMARY last_name,first_name" + "\r\n");

			ResponseFuture responseFuture = new ResponseFuture(openIndexPacket, channel.getId());
			pendingRequest.put(channel.getId(), responseFuture);
			ChannelFuture lastWriteFuture = channel.write(openIndexPacket.encode());
			//lastWriteFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			boolean result = responseFuture.get(Constants.HS_TIMEOUT);
			connectionPool.offer(channel);
			
			if(result){
				return indexId;
			}
		}
		
		return null;
	}

	public boolean requsetAuth(String authType, String authKey) throws HSJException {
		Channel channel = getChannelFromPool();
		IPacket authPacket = PacketFactory.getInstance().createAuthPacket(
				authKey);

		ResponseFuture responseFuture = new ResponseFuture(authPacket, channel.getId());
		pendingRequest.put(channel.getId(), responseFuture);
		ChannelFuture lastWriteFuture = channel.write(authPacket.encode());
		//lastWriteFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		boolean result = responseFuture.get(Constants.HS_TIMEOUT);
		connectionPool.offer(channel);
		return result;
	}

	public void requsetInsert(String indexId, List<String> values) throws HSJException {
		Channel channel = getChannelFromPool();
		
		IPacket insertPacket = PacketFactory.getInstance().createInsertPacket(indexId, values);
		//channel.write(insertPacket.encode());
		
		ResponseFuture responseFuture = new ResponseFuture(insertPacket, channel.getId());
		pendingRequest.put(channel.getId(), responseFuture);
		ChannelFuture lastWriteFuture = channel.write(insertPacket.encode());
		//lastWriteFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		boolean result = responseFuture.get(Constants.HS_TIMEOUT);
		connectionPool.offer(channel);
		//return result;
	}

	public ResultSet requsetFind(String id, CompareOperator operator,
			String[] filterValues, int limit, int offset,
			String[] filterColumnNames) {
		
		return null;
	}
	
	public void setResult(Integer id,boolean result){
		ResponseFuture future = pendingRequest.remove(id);
		future.setResult(result);
		future.setDone();
	}
	
	public void removeResponseFuture(String id){
		pendingRequest.remove(id);
	}
	private Channel getChannelFromPool() throws HSJException{
		Channel channel = null;
		try {
			channel = connectionPool.take();
		} catch (InterruptedException e) {
			//logger.error("get channel from pool error!", e);
			throw new HSJException("get channel from pool error!", e);
		}
		
		return channel;
	}
	
	private Channel getChannel(){
		boolean notTimeout = future.awaitUninterruptibly(2, TimeUnit.SECONDS);
		if(notTimeout){
			return future.awaitUninterruptibly().getChannel();
		}
		
		return null;
	}
}
