/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSClientPipelineFactory.java
 */
package com.elifes.hsj.client.netty;

import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * 描述：
 * 
 * @author yangqiang
 * @createtime 2011-11-12下午5:17:24
 * 
 */
public class HSClientPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
	    ChannelPipeline pipeline = pipeline();

	    // Add the text line codec combination first,
	    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(
	            8192, Delimiters.lineDelimiter()));
	    pipeline.addLast("decoder", new StringDecoder());
	    pipeline.addLast("encoder", new StringEncoder());

	    // and then business logic.
	    pipeline.addLast("handler", new HSClientHandler());

	    return pipeline;
	}

}
