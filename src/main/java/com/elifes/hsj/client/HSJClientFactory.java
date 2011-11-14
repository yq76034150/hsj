/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSJClientFactory.java
 */
package com.elifes.hsj.client;

import com.elifes.hsj.model.TableConfig;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午12:10:50
 *
 */
public class HSJClientFactory {
	private static HSJClientFactory self = new HSJClientFactory();
	

	public static HSJClientFactory getInstance() {
		return self;
	}

	public HSJClient createClient(String ip, int port) {
		//HSJClient hsjClient = new HSJClient(ip, port);
		//return hsjClient;
		return HSJClient.getInstance();
	}

	public void setEncoding(String encoding) {
		// TODO Auto-generated method stub
		
	}

	public void setTableConfig(TableConfig tableConfig) {
		// TODO Auto-generated method stub
		
	}

}
