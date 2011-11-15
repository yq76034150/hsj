/**
 * Copyright (c) 2011-2015 1lifes.com
 * HSJ java-handlersocket
 * Id: InsertPacket.java
 */
package com.elifes.hsj.packet;

import java.util.List;

/**
 * 描述：
 * @author yangqiang
 * @createtime 2011-11-12下午5:11:11
 *
 */
public class InsertPacket extends AbstractPacket{
	private List<String> values;
	
	public InsertPacket(String indexId, List<String> values){
		this.indexId = indexId;
		this.values = values;
	}

	@Override
	protected void encodeHeader(StringBuilder reqMsg) {
		
	}

	@Override
	protected void encodeBody(StringBuilder reqMsg) {
		reqMsg.append(autoAppendTab(indexId))
			  .append(autoAppendTab(OPERATOR_INSERT))
			  .append(autoAppendTab(String.valueOf(values.size())));
		int i = 0;
		for(String value : values){
			if(i != values.size() - 1){
				reqMsg.append(autoAppendTab(value));
			} else {
				reqMsg.append(value);
			}
			i++;
		}
	}

	
	
//	public String encode() {
//		StringBuffer requestMsg = new StringBuffer();
//		// id
//		this.writeToken(requestMsg, this.indexId);
//		this.writeTokenSeparator(requestMsg);
//		// operator
//		this.writeToken(requestMsg, OPERATOR_INSERT);
//		this.writeTokenSeparator(requestMsg);
//		// key nums
//
//		this.writeToken(requestMsg, String.valueOf(values.size()));
//		this.writeTokenSeparator(requestMsg);
//		int i = 0;
//		for (String value : values) {
//			this.writeToken(requestMsg, value);
//			if (i == values.size() - 1) {
//				this.writeCommandTerminate(requestMsg);
//			} else {
//				this.writeTokenSeparator(requestMsg);
//			}
//			i++;
//		}
//		return requestMsg.toString();
//	}

}
