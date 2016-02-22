/*
    Launch Android Client, HttpException
    Copyright (c) 2014 LAUNCH Tech Company Limited
    http:www.cnlaunch.com
 */
package com.sd.core.network.http;

/**
 * [HttpException请求异常类]
 *	
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-29
 *
 **/
public class HttpException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4010634120321127684L;

	public HttpException() {
		super();
	}

	public HttpException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public HttpException(String detailMessage) {
		super(detailMessage);
	}

	public HttpException(Throwable throwable) {
		super(throwable);
	}

	
}
