/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: ResponseFuture.java
 */
package com.elifes.hsj.client;

import com.elifes.hsj.exception.HSJException;
import com.elifes.hsj.packet.IPacket;


/**
 * 描述：
 * @author hanlin.yq
 * @createtime 2011-11-14下午02:11:41
 *
 */
public class ResponseFuture {
	
	private Integer channelId;
	private IPacket packet;
	private boolean isDone = false;
	private boolean result = false;
	
	public ResponseFuture(IPacket packet, Integer channelId){
		this.packet = packet;
		this.channelId = channelId;
	}
	
	public boolean get(long timeout){
		long end = System.currentTimeMillis()+timeout;
		synchronized(this){
			while(!isDone && timeout > 0){				
				try{
					wait(timeout);
				}catch(InterruptedException e){}
				timeout = end - System.currentTimeMillis();
			}
		}
		if(!isDone){
			packet.setExceptionMessage("connection time out!");
		}
		return result;
	}
	
	public void setDone(){
		this.isDone = true;
		synchronized(this){
			notifyAll();
		}
	}
	
	public void setResult(boolean result){
		this.result = result;
	}

}
