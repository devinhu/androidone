/*
    ShengDao Android Client, BaseActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sd.core.common.ActivityPageManager;
import com.sd.core.network.async.AsyncTaskManager;
import com.sd.core.network.async.OnDataListener;
import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NToast;
import com.sd.one.R;

/**
 * [Activity基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 * 
 **/
public class BaseActivity extends FragmentActivity implements OnDataListener{

    protected Context mContext;
    private AsyncTaskManager mAsyncTaskManager;
    
    private ViewFlipper mContentView;
    protected RelativeLayout layout_head;
    protected Button btn_left;
	protected Button btn_right;
    protected TextView tv_title;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        mContext = this;
        
        //初始化公共头部
        mContentView = (ViewFlipper) super.findViewById(R.id.layout_container);
        layout_head = (RelativeLayout) super.findViewById(R.id.layout_head);
		btn_left = (Button) super.findViewById(R.id.btn_left);
		btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_right = (Button) super.findViewById(R.id.btn_right);
		tv_title = (TextView) super.findViewById(R.id.tv_title);
		
        //初始化异步框架
		mAsyncTaskManager = AsyncTaskManager.getInstance(mContext);
		//Activity管理
		ActivityPageManager.getInstance().addActivity(this);
	}

	@Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

	@Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityPageManager.unbindReferences(mContentView);
		ActivityPageManager.getInstance().removeActivity(this);
		mContentView = null;
		mContext = null;
	}
	
	/**
	 * 发送请求（需要检查网络）
	 * @param requsetCode 请求码
	 */
	public void request(int requsetCode){
		mAsyncTaskManager.request(requsetCode, this);
	}
	
	/**
	 * 发送请求
	 * @param requsetCode 请求码
	 * @param isCheckNetwork 是否需检查网络，true检查，false不检查
	 */
	public void request(int requsetCode, boolean isCheckNetwork){
		mAsyncTaskManager.request(requsetCode, isCheckNetwork, this);
	}
	
	/**
	 * 取消所有请求
	 */
	public void cancelRequest(){
        mAsyncTaskManager.cancelRequest();
    }
	
	@Override
	public Object doInBackground(int requestCode) throws HttpException{
		return null;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		
	}

	@Override
	public void onFailure(int requestCode, int state, Object result) {
		switch(state){
			//网络不可用给出提示
			case AsyncTaskManager.HTTP_NULL_CODE:
				NToast.shortToast(mContext, R.string.common_network_unavailable);
				break;
				
			//网络有问题给出提示
			case AsyncTaskManager.HTTP_ERROR_CODE:
				NToast.shortToast(mContext, R.string.common_network_error);
				break;
				
			//请求有问题给出提示
			case AsyncTaskManager.REQUEST_ERROR_CODE:
				NToast.shortToast(mContext, R.string.common_request_error);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 重新启动当前页面 
	 */
	protected void reLoad(){
	    Intent intent = getIntent();
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();
	    overridePendingTransition(0, 0);
	    startActivity(intent);
	}
	
	/**
	 * 设置头部是否可见
	 * @param visibility
	 */
	public void setHeadVisibility(int visibility) {
		layout_head.setVisibility(visibility);
	}

	/**
	 *设置标题
	 */
	public void setTitle(int titleId) {
		tv_title.setText(getString(titleId));
	}

	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
	}
}
