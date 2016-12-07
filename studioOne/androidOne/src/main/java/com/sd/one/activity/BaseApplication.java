/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.app.Application;
import android.text.TextUtils;

import com.bugtags.library.Bugtags;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.sd.core.common.CacheManager;
import com.sd.core.utils.NLog;
import com.sd.one.utils.CommonUtils;

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

        //初始化Fresco
        Fresco.initialize(this);

        //设置默认缓存路径
        CacheManager.setSysCachePath(getCacheDir().getPath());

        //集成Bugtags，debug模式可以提测试，正式环境只收集错误日志
        if (Boolean.parseBoolean(flag)) {
            Bugtags.start("4a1d8ec8eab29fccf4f86a24039c1951", this, Bugtags.BTGInvocationEventBubble);
        } else {
            Bugtags.start("4a1d8ec8eab29fccf4f86a24039c1951", this, Bugtags.BTGInvocationEventNone);
        }
    }
}
