/*
    ShengDao Android Client, GetAreaResponse
    Copyright (c) 2015 ShengDao Tech Company Limited
*/

package com.sd.one.model.response;

import java.util.List;

import com.sd.one.model.Area;
import com.sd.one.model.base.BaseResponse;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-11-10
 * 
 **/
public class GetAreaResponse extends BaseResponse {

	/** serialVersionUID **/ 
	private static final long serialVersionUID = 9198617763785516785L;
	
	private List<Area> data;

	public List<Area> getData() {
		return data;
	}

	public void setData(List<Area> data) {
		this.data = data;
	}

	
	
}
