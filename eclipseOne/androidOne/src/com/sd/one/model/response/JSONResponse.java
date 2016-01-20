/*
    ShengDao Android Client, JSONResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.response;

import com.sd.one.model.base.BaseModel;

/**
 * [JSON返回结果演示类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class JSONResponse extends BaseModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1922894685688737511L;

	private String fs_id;
	private String path;
	private String ctime;
	private String mtime;
	private String request_id;

	public String getFs_id() {
		return fs_id;
	}

	public void setFs_id(String fs_id) {
		this.fs_id = fs_id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	@Override
	public String toString() {
		return "JSONResponse [fs_id=" + fs_id + ", path=" + path + ", ctime="
				+ ctime + ", mtime=" + mtime + ", request_id=" + request_id
				+ "]";
	}

}
