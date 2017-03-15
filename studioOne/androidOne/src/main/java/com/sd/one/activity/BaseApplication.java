/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.app.Application;
import android.text.TextUtils;
import com.sd.one.utils.CommonUtils;
import com.sd.one.utils.NLog;

/**
 * [系统Application类，设置全局变量以及初始化组件]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 **/
public class BaseApplication extends Application {

    private final String tag = BaseApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化debug模式
        String flag = CommonUtils.getProperty(getApplicationContext(), "debug");
        if (!TextUtils.isEmpty(flag)) {
            Boolean isDebug = Boolean.parseBoolean(flag);
            NLog.setDebug(isDebug);
            NLog.e(tag, "isDebug: " + isDebug);
        }

//        IMSSdk
    }
}
