package com.sd.core.network.okhttp;

import android.content.Context;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpUtils {

    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

    private static OkHttpUtils instance;
    private OkHttpClient client;

    /**
     * 构造方法
     * @return
     */
    private OkHttpUtils(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore1(context)));
        client = builder.build();
    }

    /**
     * 获取单例instance
     * @return
     */
    public static OkHttpUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取OkHttpClient
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return client;
    }


    public String get(String url) throws Exception{
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.get().url(url).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().toString();
        }
        return null;
    }


    public String get(String url, RequestParams1 params) throws Exception{
        url = params.getParamString(url);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.get().url(url).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().toString();
        }
        return null;
    }


    public String post(String url, RequestParams1 params) throws Exception{
        String tag = String.valueOf(url.hashCode());
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(params.getRequestBody()).url(url).tag(tag).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().toString();
        }
        return null;
    }


    /** 根据Tag取消请求 */
    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

