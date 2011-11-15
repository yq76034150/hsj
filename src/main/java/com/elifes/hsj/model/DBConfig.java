/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: DBInfo.java
 */
package com.elifes.hsj.model;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午12:24:27
 *
 */
public class DBConfig {
	private String ip;
	private int port;
	private String dbName;
	private String authKey;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

}
