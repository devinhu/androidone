package com.sd.one.model.response;

import java.util.List;

import com.sd.one.model.ChannelData;
import com.sd.one.model.base.BaseResponse;

/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class ChannelResponse extends BaseResponse {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private List<ChannelData> data;

	public List<ChannelData> getData() {
		return data;
	}

	public void setData(List<ChannelData> data) {
		this.data = data;
	}
    
}
