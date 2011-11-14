/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: AbstractPacket.java
 */
package com.elifes.hsj.packet;

import java.io.UnsupportedEncodingException;

import com.elifes.hsj.util.Constants;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12上午11:51:40
 *
 */
public abstract class AbstractPacket implements IPacket{
	public static final byte TOKEN_SEPARATOR = 0x09;
	public static final byte COMMAND_TERMINATE = 0x0a;

	public static final String OPERATOR_AUTH = "A";
	public static final String OPERATOR_OPEN_INDEX = "P";
	public static final String OPERATOR_INSERT = "+";
	public static final String OPERATOR_UPDATE = "U";
	public static final String OPERATOR_DELETE = "D";
	
	protected String encoding = Constants.DEFAULT_ENCODING;
	
	protected String indexId;
	
	public byte[] decodeString(String s) {
		try {
			return s.getBytes(this.encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported encoding:" + this.encoding,
					e);
		}
	}
	
	protected void writeToken(StringBuffer requestMsg, String token) {
		if (token == null) {
			requestMsg.append((byte) 0x00);
		} else {
			byte[] bytes = decodeString(token);
			writeToken(requestMsg, bytes);
		}
	}

	protected void writeToken(StringBuffer requestMsg, byte[] token) {
		if (token == null) {
			requestMsg.append((byte) 0x00);
		} else {
			for (byte b : token) {
				if (b >= 0 && b <= 0x0f) {
					requestMsg.append((byte) 0x01);
					requestMsg.append((byte) (b | 0x40));
				} else {
					requestMsg.append(b);
				}
			}
		}
	}
	
	protected void writeTokenSeparator(StringBuffer requestMsg) {
		requestMsg.append(TOKEN_SEPARATOR);
	}

	protected void writeCommandTerminate(StringBuffer requestMsg) {
		requestMsg.append(COMMAND_TERMINATE);
	}

}
