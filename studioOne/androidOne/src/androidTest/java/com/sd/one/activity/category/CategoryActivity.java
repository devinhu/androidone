/*
    ShengDao Android Client, CategoryActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.category;

import android.os.Bundle;
import android.view.KeyEvent;

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
public class CategoryActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_layout);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}
}
