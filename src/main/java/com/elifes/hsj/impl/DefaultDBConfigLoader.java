/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: DefaultDBConfigLoader.java
 */
package com.elifes.hsj.impl;

import java.io.IOException;
import java.util.Properties;

import com.elifes.hsj.IDBConfigLoader;
import com.elifes.hsj.model.DBConfig;
import com.elifes.hsj.util.Constants;
import com.elifes.hsj.util.ResourceUtil;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-13下午4:25:09
 *
 */
public class DefaultDBConfigLoader implements IDBConfigLoader {
	private static final String DB_CONFIG_NAME = "db.properties";
	private static Properties dbCfg;
	
	static{
		try {
			dbCfg = ResourceUtil.getResourceAsProperties(DB_CONFIG_NAME);
		} catch (IOException e) {
			
		}
	}

	/* (non-Javadoc)
	 * @see com.elifes.hsj.IDBConfigLoader#load()
	 */
	public DBConfig load() {
		DBConfig dbConfig = new DBConfig();
		
		dbConfig.setIp(dbCfg.getProperty(Constants.IP_LABEL));
		dbConfig.setPort(Integer.valueOf(dbCfg.getProperty(Constants.PORT_LABEL)));
		dbConfig.setDbName(dbCfg.getProperty(Constants.DB_NAME_LABEL));
		dbConfig.setAuthKey(dbCfg.getProperty(Constants.AUTH_KEY_LABEL));
		
		return dbConfig;
	}

}
