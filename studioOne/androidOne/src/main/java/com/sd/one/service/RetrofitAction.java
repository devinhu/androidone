package com.sd.one.service;

import android.content.Context;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.ConfigData;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/12/14
 */
public class RetrofitAction extends RetrofitManager {

    /**
     * 构造方法
     * @param mContext
     */
    public RetrofitAction(Context mContext) {
        super(mContext);
    }

    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse<List<ConfigData>>> getConfig() {
        HashMap params = getRequestParams();
        return apiService.getConfig(params);
    }

}
