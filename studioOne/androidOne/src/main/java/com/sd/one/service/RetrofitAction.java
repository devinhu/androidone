package com.sd.one.service;

import android.content.Context;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.ConfigData;
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

    private ApiService apiService;

    /**
     * 构造方法
     * @param mContext
     */
    public RetrofitAction(Context mContext) {
        super(mContext);
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }



    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse<List<ConfigData>>> getConfig() {
        return apiService.getConfig();
    }

    /**
     * 获取圈子列表接口
     * @return
     */
    public Call<BaseResponse<List<ConfigData>>> getCircleTypeList(String patientId) {
        return apiService.getCircleTypeList(patientId);
    }


}
