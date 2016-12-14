package com.sd.one.service;

import android.content.Context;

import com.sd.core.network.http.HttpException;
import com.sd.one.model.response.ConfigResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

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
        super();
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
     * @throws HttpException
     */
    public void getConfig(Subscriber<ConfigResponse> subscriber) {
        Observable observable = apiService.getConfig();
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取圈子列表接口
     * @return
     * @throws HttpException
     */
    public void getCircleTypeList(Subscriber<ConfigResponse> subscriber, String patientId) {
        Observable observable = apiService.getCircleTypeList(patientId);
        toSubscribe(observable, subscriber);
    }

    public void getALL(Subscriber<ConfigResponse> subscriber, String patientId) {
        Observable observable = apiService.getCircleTypeList(patientId);
        Observable configObservable = apiService.getConfig();

        Observable.zip(configObservable, observable, new Func2() {
            @Override
            public Object call(Object o, Object o2) {
                return null;
            }
            }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
