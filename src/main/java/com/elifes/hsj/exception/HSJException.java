/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: HSJException.java
 */
package com.elifes.hsj.exception;

/**
 * 描述：
 * @author hanlin.yq
 * @createtime 2011-11-14下午02:53:40
 *
 */
public class HSJException extends Exception {
	private static final long serialVersionUID = -1433655740312164143L;

	public HSJException(String message){
		super(message);
	}
	
	public HSJException(String message, Throwable cause){
		super(message, cause);
	}
}
