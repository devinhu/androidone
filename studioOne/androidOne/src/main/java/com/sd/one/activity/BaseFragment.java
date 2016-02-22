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

import com.sd.core.common.ActivityPageManager;
import com.sd.core.network.async.AsyncTaskManager;
import com.sd.core.network.async.OnDataListener;
import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NToast;
import com.sd.one.R;

/**
 * [Fragment基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public abstract class BaseFragment extends Fragment implements OnDataListener{

	protected Context mContext;
	private View mContentView = null;
	private AsyncTaskManager mAsyncTaskManager;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		//初始化异步框架
		mAsyncTaskManager = AsyncTaskManager.getInstance(mContext);
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
}
