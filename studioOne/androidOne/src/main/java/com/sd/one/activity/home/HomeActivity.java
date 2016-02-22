/*
    ShengDao Android Client, HomeActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class HomeActivity extends BaseActivity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		
	}
}
