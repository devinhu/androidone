package com.sd.one.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.demo.database.DatabaseActivity;
import com.sd.one.activity.demo.download.DownLoadActivity;
import com.sd.one.activity.demo.pulltorefresh.PullToRefreshActivity;

/**   
 * [首页]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 28, 2013    
 */
public class HomeActivity extends BaseActivity implements OnClickListener{

	private Button btn_request;
	private Button btn_download;
	private Button btn_database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		
		btn_request = (Button)findViewById(R.id.btn_request);
		btn_request.setOnClickListener(this);
		btn_download = (Button)findViewById(R.id.btn_download);
		btn_download.setOnClickListener(this);
		btn_database = (Button)findViewById(R.id.btn_database);
		btn_database.setOnClickListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_request:
				startActivity(new Intent(mContext, PullToRefreshActivity.class));
				break;
				
			case R.id.btn_download:
				startActivity(new Intent(mContext, DownLoadActivity.class));
				break;
				
			case R.id.btn_database:
				startActivity(new Intent(mContext, DatabaseActivity.class));
				break;
		}
	}
}
