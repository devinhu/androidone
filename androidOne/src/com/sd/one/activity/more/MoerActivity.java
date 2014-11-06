package com.sd.one.activity.more;

import android.os.Bundle;
import android.view.KeyEvent;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;

/**
 * [更多页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class MoerActivity extends BaseActivity {

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
