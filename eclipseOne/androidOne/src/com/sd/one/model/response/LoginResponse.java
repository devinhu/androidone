/*
    ShengDao Android Client, LoginResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.response;

import com.sd.one.model.UserData;
import com.sd.one.model.base.BaseResponse;

/**
 * [登陆返回结果演示类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class LoginResponse extends BaseResponse {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

}
