package com.sd.one.widget.sortlistview;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-7
 * 
 **/
public class SortModel {

	// 显示的数据
	private String name;

	// 显示数据拼音的首字母
	private String sortLetters;

	private String deviceId;

	private String userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
