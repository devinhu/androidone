package com.sd.one.common.okhttp;

import android.content.Context;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;


public class OkHttpUtils {

    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

    private static OkHttpUtils instance;
    private OkHttpClient client;

    private String CER = "-----BEGIN CERTIFICATE-----\n" +
            "MIICUDCCAbmgAwIBAgIEJs69NTANBgkqhkiG9w0BAQsFADBaMQ0wCwYDVQQGEwQoQ04pMQ0wCwYD\n" +
            "VQQIEwQoQkopMQ0wCwYDVQQHEwQoQkopMQ0wCwYDVQQKEwQoU1MpMQ0wCwYDVQQLEwQoU1MpMQ0w\n" +
            "CwYDVQQDEwQoU1MpMCAXDTE2MTIwNzE0MzMyNloYDzIxMTYxMTEzMTQzMzI2WjBaMQ0wCwYDVQQG\n" +
            "EwQoQ04pMQ0wCwYDVQQIEwQoQkopMQ0wCwYDVQQHEwQoQkopMQ0wCwYDVQQKEwQoU1MpMQ0wCwYD\n" +
            "VQQLEwQoU1MpMQ0wCwYDVQQDEwQoU1MpMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoVGN0\n" +
            "VNkRyOPgpK5QXHUhfiWWDtyztFCaRq2C8tBkIsDBV3XkC0yzb/5vQBLRnHgV3sEcrYHkBqZ4u0XQ\n" +
            "ZXpAm6Jd0jpn+CMXZbI4WxsjnjVFBZO8Mb+l0kVCCofssWpkOBtF0xT0A8yvzyHYK4ydjjAkq7ug\n" +
            "0MgwqSmh5eS4RQIDAQABoyEwHzAdBgNVHQ4EFgQUW8DIhb9EYLXFuEoHW1Dzyyy+z5gwDQYJKoZI\n" +
            "hvcNAQELBQADgYEAc4LoGujDA5WvQ5LK2kffpt8rIsHEX5Yi4gSukEEA+VXmg3RhxPk9mqNS7U54\n" +
            "En481mHpiO0/AfAmh17a3cbSDZ4BuRn/xkd/uJ05Tk4/C3aXdgDjE6LWNaCuhoSr3p4kzOOI811L\n" +
            "G4IsNLJZi6HzWlLhQV21WlWmbLKSl/aBWzE=\n" +
            "-----END CERTIFICATE-----";

    /**
     * 构造方法
     * @return
     */
    private OkHttpUtils(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{new Buffer().writeUtf8(CER).inputStream()}, null, null);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(logging)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
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


    public String get(String url, RequestParams params) throws Exception{
        url = params.getParamString(url);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.get().url(url).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().toString();
        }
        return null;
    }


    public String post(String url, RequestParams params) throws Exception{
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

