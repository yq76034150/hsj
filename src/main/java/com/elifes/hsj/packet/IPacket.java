/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: IPacket.java
 */
package com.elifes.hsj.packet;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:06:28
 *
 */
public interface IPacket {
	public String encode();
	
	public void setExceptionMessage(String t);

	public String getExceptionMessage();
}
