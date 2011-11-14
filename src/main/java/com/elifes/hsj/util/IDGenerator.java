/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: IDGenerator.java
 */
package com.elifes.hsj.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午9:51:18
 *
 */
public class IDGenerator {
	private static AtomicInteger generator = new AtomicInteger(0);

	public static int nextId(){
		return generator.incrementAndGet();
	}
}
