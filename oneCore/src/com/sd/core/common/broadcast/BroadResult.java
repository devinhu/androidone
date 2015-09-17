/*
    ShengDao Android Client, BroadResult
    Copyright (c) 2015 ShengDao Tech Company Limited
 */

package com.sd.core.common.broadcast;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-9-17
 * 
 **/
public class BroadResult {

	/** 请求code **/
	private int broadCode;
	/** 返回结果 **/
	private Object result;
	
	/**
	 * 
	 */
	public BroadResult() {
		super();
	}

	/**
	 * @param broadCode
	 */
	public BroadResult(int broadCode) {
		super();
		this.broadCode = broadCode;
	}

	/**
	 * @param broadCode
	 * @param result
	 */
	public BroadResult(int broadCode, Object result) {
		super();
		this.broadCode = broadCode;
		this.result = result;
	}

	public int getBroadCode() {
		return broadCode;
	}

	public void setBroadCode(int broadCode) {
		this.broadCode = broadCode;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
