package com.sd.one.service;

import com.sd.one.model.response.ConfigResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/10/11
 */
public interface ApiService {

    @GET("/app/sys/getConfig")
    Observable<ConfigResponse> getConfig();


    @GET("/app/circle/getCircleTypeList")
    Observable<ConfigResponse> getCircleTypeList(@Query("patientId") String patientId);
}
