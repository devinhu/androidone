/*
    ShengDao Android Client, BaseResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.base;

import android.text.TextUtils;

/**
 * [返回结果基类，返回结果公共字段本类实现]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class BaseResponse extends BaseModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5616901114632786764L;

	private String resultCode;

	private String errorMsg;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSucces() {
		if (!TextUtils.isEmpty(resultCode) && "1".equals(resultCode)) {
			return true;
		}
		return false;
	}
}
