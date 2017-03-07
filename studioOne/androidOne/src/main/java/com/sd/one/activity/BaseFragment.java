/*
    ShengDao Android Client, BaseFragment
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sd.one.R;
import com.sd.one.common.async.AsyncTaskManager;
import com.sd.one.common.async.HttpException;
import com.sd.one.common.async.OnDataListener;
import com.sd.one.common.manager.ActivityPageManager;
import com.sd.one.utils.NToast;

/**
 * [Fragment基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public abstract class BaseFragment extends Fragment implements OnDataListener {

	protected Context mContext;
	private View mContentView = null;
	private AsyncTaskManager mAsyncTaskManager;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		//初始化异步框架
		mAsyncTaskManager = AsyncTaskManager.getInstance(mContext.getApplicationContext());
	}
	  
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    mContentView = onCreateFragmentView(inflater, container, savedInstanceState);
	    return mContentView;
    }

    @Override
    public void onDestroy() {
		super.onDestroy();
		ActivityPageManager.unbindReferences(mContentView);
		mContentView = null;
		mContext = null;
	}
    
    /**
     * 创建view方法，子类必须重写
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
    /**
	 * 发送请求（需要检查网络）
	 * @param requestCode 请求码
	 */
	public void request(int requestCode){
		mAsyncTaskManager.request(requestCode, this);
	}
	
	/**
	 * 发送请求
	 * @param requestCode 请求码
	 * @param isCheckNetwork 是否需检查网络，true检查，false不检查，主要是用于有时候网络请求从缓存里面取的
	 */
	public void request(int requestCode, boolean isCheckNetwork){
		mAsyncTaskManager.request(requestCode, isCheckNetwork, this);
	}
	

	@Override
	public Object doInBackground(int requestCode) throws HttpException {
		//TODO 处理异步方法
		return null;
	}

	@Override
	public boolean onIntercept(int requestCode, Object result) {
		//TODO 返回true表示打断，false表示继续执行onSuccess方法
		return false;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		//TODO 处理成功的逻辑
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
}
