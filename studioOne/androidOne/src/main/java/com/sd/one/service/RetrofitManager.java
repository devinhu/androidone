package com.sd.one.service;

import android.content.Context;

import com.sd.one.common.Constants;
import com.sd.one.common.okhttp.OkHttpUtils;
import com.sd.one.model.response.ConfigResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/12/14
 */
public abstract class RetrofitManager {

    protected Retrofit retrofit;

    /**
     * 构造方法
     */
    public RetrofitManager(Context context) {
        retrofit = new Retrofit.Builder()
                .client(OkHttpUtils.getInstance(context).getOkHttpClient())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.DOMAIN)
                .build();
    }

    public void toSubscribe(Observable observable, Subscriber subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
