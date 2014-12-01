/*
    ShengDao Android Client, PhotoActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.demo.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.sd.one.activity.BaseActivity;
import com.sd.one.utils.FileUtils;
import com.sd.one.utils.StringUtils;
import com.sd.one.utils.photo.CropHandler;
import com.sd.one.utils.photo.CropHelper;
import com.sd.one.utils.photo.CropParams;

/**
 * [相册页面]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-12-1
 * 
 **/
public class PhotoActivity extends BaseActivity implements CropHandler {

	private CropParams mCropParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCropParams = new CropParams();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CropHelper.REQUEST_CROP:
		case CropHelper.REQUEST_CAMERA:
			CropHelper.handleResult(this, requestCode, resultCode, data);
			break;

		}
	}

	@Override
	public void onPhotoCropped(Uri uri) {
		String fileName = StringUtils.getImageName(uri);
		FileUtils.getInstance().saveFile(CropHelper.decodeUriAsBitmap(this, mCropParams.uri), fileName);
		String filePath = FileUtils.getInstance().getFilePath(fileName);
	}

	@Override
	public void onCropCancel() {

	}

	@Override
	public void onCropFailed(String message) {

	}

	@Override
	public CropParams getCropParams() {
		return mCropParams;
	}

	@Override
	public Activity getContext() {
		return this;
	}

	@Override
	protected void onDestroy() {
		if (getCropParams() != null) {
			CropHelper.clearCachedCropFile(getCropParams().uri);
		}
		super.onDestroy();
	}
}
