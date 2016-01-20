/*
    Launch Android Client, DownloadInfo
    Copyright (c) 2014 LAUNCH Tech Company Limited
    http:www.cnlaunch.com
 */

package com.sd.one.activity.demo.download;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-13
 * 
 **/
public class DownloadInfo {

	private String url;
	private int progress;
	private String name;
	private String state;

	/**
	 * @param url
	 * @param name
	 */
	public DownloadInfo(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
