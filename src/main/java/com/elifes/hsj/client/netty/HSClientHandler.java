/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSClientHandler.java
 */
package com.elifes.hsj.client.netty;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.elifes.hsj.client.HSJClient;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:18:02
 *
 */
public class HSClientHandler extends SimpleChannelUpstreamHandler{
	private static final Logger logger = Logger.getLogger(
			HSClientHandler.class.getName());
	
	private HSJClient hsjClient;
	public HSClientHandler(HSJClient hsjClient){
		this.hsjClient = hsjClient;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		String responseMsg = (String)e.getMessage();
		
		if("0	1".equals(responseMsg)){
			hsjClient.setResult(e.getChannel().getId(), true);
		} else if("0".startsWith(responseMsg)){
			
		} else{
			
		}
		//HSJClient.getInstance()
		System.err.println(e.getMessage());
		super.messageReceived(ctx, e);
	}
	
    @Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		super.exceptionCaught(ctx, e);
	}

}
