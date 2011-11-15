/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: CompareOperator.java
 */
package com.elifes.hsj.model;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午12:34:26
 *
 */
public enum CompareOperator {
	/**
	 * '=' operator
	 */
	EQ,
	/**
	 * '>' operator
	 */
	GT,
	/**
	 * '>=' operator
	 */
	GE,
	/**
	 * '<=' opeartor
	 */
	LE,
/**
	 * '<' opeartor
	 */
	LT;

	/**
	 * Returns operator string value
	 * 
	 * @return
	 */
	public String getValue() {
		switch (this) {
		case EQ:
			return "=";
		case GT:
			return ">";
		case GE:
			return ">=";
		case LE:
			return "<=";
		case LT:
			return "<";
		default:
			throw new RuntimeException("Unknow find operator " + this);
		}
	}


}
