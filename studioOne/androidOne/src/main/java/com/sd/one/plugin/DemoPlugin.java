package com.sd.one.plugin;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONException;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.sd.core.common.ActivityPageManager;
import com.sd.core.common.LruCacheManager;
import com.sd.core.utils.NLog;
import com.sd.one.R;
import com.sd.one.utils.CommonUtils;
import com.sd.one.widget.dialog.MessageDialog;

/**
 * [插件演示类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-7-31
 * 
 **/
public class DemoPlugin extends CordovaPlugin {

	private final String tag = DemoPlugin.class.getSimpleName();
	private Activity activity;

	@Override
	public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {

		NLog.e(tag, "action: " + action);
		activity = ((Activity) cordova.getActivity());

		if (TextUtils.isEmpty(action)) {
			NLog.e(tag, "sorry, the action is not null.");
			return false;
		}

		// 判断是否有网络
		if ("checkNetWork".equals(action)) {
			if (CommonUtils.isNetworkConnected(activity)) {
				callbackContext.success("true");
			} else {
				callbackContext.error("您的网络有问题，请稍后重试！");
			}
			return true;
		}

		// 退出程序
		if ("exit".equals(action)) {
			MessageDialog dialog = new MessageDialog(activity, activity.getString(R.string.common_exit));
			dialog.setBtn1ClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LruCacheManager.getInstance().evictAll();
					ActivityPageManager.getInstance().exit(activity);
				}
			});
			dialog.show();
			return true;
		}

		return super.execute(action, args, callbackContext);
	}
}
