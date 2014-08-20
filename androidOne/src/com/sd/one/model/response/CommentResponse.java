package com.sd.one.model.response;

import com.sd.one.model.CommentData;
import com.sd.one.model.base.BaseResponse;

/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class CommentResponse extends BaseResponse {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private CommentData data;

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }

}
