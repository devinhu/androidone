/*
    ShengDao Android Client, Area
    Copyright (c) 2015 ShengDao Tech Company Limited
 */

package com.sd.one.model;

import com.sd.one.model.base.BaseModel;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-11-10
 * 
 **/
public class Area extends BaseModel {

	/** serialVersionUID **/
	private static final long serialVersionUID = 2520681091695829963L;

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Area [code=" + code + ", name=" + name + "]";
	}

}
