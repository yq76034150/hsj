/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: IDGenerator.java
 */
package com.elifes.hsj.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午9:51:18
 *
 */
public class IDGenerator {
	private static AtomicLong generator = new AtomicLong(0L);

	public static long nextId(){
		return generator.incrementAndGet();
	}
}
