package com.sd.one.model.response;

import com.sd.one.model.ContentData;
import com.sd.one.model.base.BaseResponse;

/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class ContentResponse extends BaseResponse {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private ContentData data;

    public ContentData getData() {
        return data;
    }

    public void setData(ContentData data) {
        this.data = data;
    }

}
