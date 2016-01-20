/*
    ShengDao Android Client, PhotoActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.demo.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.utils.photo.PhotoParams;
import com.sd.one.utils.photo.PhotoUtils;
import com.sd.one.utils.photo.PhotoUtils.OnPhotoResultListener;

/**
 * [相册页面]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-12-1
 * 
 **/
public class PhotoActivity extends BaseActivity implements OnClickListener{

	private PhotoUtils photoUtils;
	private PhotoParams photoParams;
	
	private Button btn_select;
	private Button btn_take;
	private ImageView img_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_demo_photo);
		
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_select.setOnClickListener(this);
		btn_take = (Button) findViewById(R.id.btn_take);
		btn_take.setOnClickListener(this);
		img_result = (ImageView) findViewById(R.id.img_result);
		
		photoParams = new PhotoParams();
		photoUtils = new PhotoUtils(this);
		photoUtils.setOnPhotoResultListener(new OnPhotoResultListener() {
			@Override
			public void onPhotoResult(Uri uri) {
				Log.e("onPhotoResult", uri.getPath().toString());
			}

			@Override
			public void onPhotoCancel() {
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select:
			//此处photoParams必须为新构建的实例对象，你可修改参数, 如：photoParams.crop = String.valueOf(false);等
			photoParams = new PhotoParams();
			photoUtils.selectPicture(this, photoParams);
			break;
			
		case R.id.btn_take:
			photoParams = new PhotoParams();
			//此处photoParams必须为新构建的实例对象，你可修改参数, 如：photoParams.crop = String.valueOf(false);等
			photoUtils.takePicture(this, photoParams);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		photoUtils.onActivityResult(requestCode, resultCode, data);
	}
	
}
