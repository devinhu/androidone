/*
    ShengDao Android Client, SplashActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.sd.core.common.PreferencesManager;
import com.sd.core.network.http.HttpException;
import com.sd.one.R;
import com.sd.one.common.Constants;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.GetAreaResponse;
import com.sd.one.service.DemoAction;

/**
 * [预加载页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class SplashActivity extends BaseActivity {

	private final String tag = SplashActivity.class.getSimpleName();
	private final int TEST_CODE_1 = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * What is happening:
		 * 
		 * (This refers to an application that has not set any launchMode
		 * settings and so is using the defaults)
		 * 
		 * You launch an app from the Market or from the Installer. This
		 * launches the root/main activity of the application with the
		 * FLAG_ACTIVITY_NEW_TASK flag and no categories. Right now the
		 * applications stack is [ A ]
		 * 
		 * Then you proceed to the next activity in the application. Now the
		 * stack in this task is [ A > B ]
		 * 
		 * Then you press the home key and then relaunch the same application by
		 * pressing it's icon from either the home screen or the app tray.
		 * 
		 * What is expected at this point is that activity B will show, since
		 * that is where you left off. However A is shown and the tasks stack is
		 * [ A > B > A ] This second instance of A is launched with the
		 * following flags: FLAG_ACTIVITY_NEW_TASK,
		 * FLAG_ACTIVITY_RESET_IF_NEEDED, and FLAG_ACTIVITY_BROUGHT_TO_FRONT. It
		 * also has the android.intent.category.LAUNCHER category.
		 * 
		 * At this point, if you hit the back key, it will return you to B, as
		 * it was when you left it.
		 * 
		 * Looking at the documentation it seems as if
		 * FLAG_ACTIVITY_BROUGHT_TO_FRONT should only be set for activities that
		 * use the singleTask or singleTop launchModes. However, this
		 * application has not set any launchModes and is therefore using the
		 * default standard launchMode.
		 * 
		 * I assume this is not suppose to happen in this case?
		 * 
		 * I should also note, that once it gets into this weird state, then it
		 * happens everytime the app is launched from the home screen or app
		 * tray. If the task is finished (restarting the phone, force stopping
		 * the app, or hitting back all the way through the stack) will fix this
		 * issue and will no longer launch it incorrectly. It only happens if
		 * you launch the app from the installer or market and then try to
		 * launch it from the launcher.
		 * 
		 * So in summary, why is this happening? Is there a way to prevent it?
		 * 
		 * Workaround
		 * 
		 * In the main/root activity's onCreate method, check if the intent has
		 * the FLAG_ACTIVITY_BROUGHT_TO_FRONT set and if so, call finish(). This
		 * then pops the extra instance of A off the stack [ A > B > A ] becomes
		 * [ A > B ] and from the users perspective, it launches into the
		 * activity they were expecting.
		 */
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) {
		    super.onCreate(savedInstanceState);
			finish();
			return;
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		setHeadVisibility(View.GONE);
		
		request(TEST_CODE_1);
//		intoMainPage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public Object doInBackground(int requsetCode) throws HttpException {
		DemoAction action = new DemoAction(mContext);
		return action.getLastedVersion();
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		if(result != null){
			BaseResponse res = (BaseResponse)result;
			if(res.isSucces()){
				intoMainPage();
			}
		}
	}

	@Override
	public void onFailure(int requestCode, int state, Object result) {
		super.onFailure(requestCode, state, result);
		intoMainPage();
	}
	
	public void intoMainPage(){
		boolean isFirstRun = PreferencesManager.getInstance(mContext).get(Constants.IS_FIRST_RUN, true);
		if(isFirstRun){
			Intent intent = new Intent(mContext, GuideActivity.class);
			startActivity(intent);
			finish();
		}else{
			Intent intent = new Intent(mContext, MainActivity.class);
			startActivity(intent);
			finish();
		} 
	}
}
