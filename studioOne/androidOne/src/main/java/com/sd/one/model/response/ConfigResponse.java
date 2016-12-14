package com.sd.one.model.response;


import com.sd.one.model.base.BaseResponse;

import java.util.List;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/5/5
 */
public class ConfigResponse extends BaseResponse {

    private List<ConfigData> data;

    public List<ConfigData> getData() {
        return data;
    }

    public void setData(List<ConfigData> data) {
        this.data = data;
    }
}
