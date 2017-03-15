/*
    ShengDao Android Client, SplashActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.sd.one.R;
import com.sd.one.common.Constants;
import com.sd.one.common.async.HttpException;
import com.sd.one.common.manager.PreferencesManager;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.ConfigData;
import com.sd.one.service.RetrofitAction;
import com.sd.one.utils.NLog;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;

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
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) {
		    super.onCreate(savedInstanceState);
			finish();
			return;
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		setHeadVisibility(View.GONE);

		request(TEST_CODE_1);request(TEST_CODE_1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public Object doInBackground(int requestCode) throws HttpException {
		try {
			RetrofitAction action = new RetrofitAction(mContext.getApplicationContext());
			switch (requestCode){
				case TEST_CODE_1:{
					Call<BaseResponse<List<ConfigData>>> call = action.getConfig();
					return call.execute().body();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(e);
		}
		return null;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		if(result != null){
			switch (requestCode){
				case TEST_CODE_1:{
					BaseResponse<List<ConfigData>> res = (BaseResponse<List<ConfigData>>)result;
					if(res.isSucces()){
						intoMainPage();
					}
				}
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
