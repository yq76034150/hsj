/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: CollectionUtil.java
 */
package com.elifes.hsj.util;

import java.util.Collection;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午11:20:48
 *
 */
public class CollectionUtil {
	private static final String JOIN_KEY = ",";
	public static String join(Collection<String> collection){
		StringBuffer joinStr = new StringBuffer();
		int i = 0;
		for(String val : collection){
			if(i != collection.size() - 1){
				joinStr.append(val).append(JOIN_KEY);
			} else {
				joinStr.append(val);
			}
			i++;
		}
		
		return joinStr.toString();
	}
}
