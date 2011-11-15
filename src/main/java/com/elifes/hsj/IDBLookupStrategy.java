/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: IDBLookupStrategy.java
 */
package com.elifes.hsj;

import com.elifes.hsj.model.TableConfig;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午1:51:11
 *
 */
public interface IDBLookupStrategy {
	public TableConfig lookup(String lookupKey);
}
