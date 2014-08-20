package com.sd.one.model.response;

import com.sd.one.model.ContentInfoData;
import com.sd.one.model.base.BaseResponse;

/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class ContentInfoResponse extends BaseResponse {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private ContentInfoData data;

    public ContentInfoData getData() {
        return data;
    }

    public void setData(ContentInfoData data) {
        this.data = data;
    }

}
