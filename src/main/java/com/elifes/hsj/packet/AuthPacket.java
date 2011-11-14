/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: AuthPacket.java
 */
package com.elifes.hsj.packet;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:10:28
 *
 */
public class AuthPacket extends AbstractPacket{
	private String authType = "1";
	private String authKey;
	
	public AuthPacket(String authKey){
		this.authKey = authKey;
	}

	@Override
	protected void encodeHeader(StringBuilder reqMsg) {
		reqMsg.append(autoAppendTab(OPERATOR_AUTH));
	}

	@Override
	protected void encodeBody(StringBuilder reqMsg) {
		reqMsg.append(autoAppendTab(authType)).append(authKey);
	}
}
