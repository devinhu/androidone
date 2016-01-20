/*
    Launch Android Client, TestFileUtils
    Copyright (c) 2014 LAUNCH Tech Company Limited
    http:www.cnlaunch.com
*/

package com.sd.test.utils;

import android.graphics.Bitmap;

import com.sd.core.utils.NLog;
import com.sd.one.R;
import com.sd.one.utils.BitmapUtils;
import com.sd.one.utils.FileUtils;
import com.sd.test.BaseTestCase;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-19
 * 
 **/
public class TestFileUtils extends BaseTestCase {

	public void test_saveFile(){
		
		FileUtils fu = FileUtils.getInstance();
		
		Bitmap bitmap = BitmapUtils.getResBitmap(getContext(), R.drawable.ic_launcher);
		fu.saveFile(bitmap, "demo_bitmap.png");
		NLog.e("test_saveFile", fu.getFilePath("demo_bitmap.png"));
		
		fu.saveFile("StringStringString", "demo_bitmap1.txt");
		String sssss = fu.readFile(fu.getFilePath("demo_bitmap1.txt"));
		NLog.e("sssss", sssss);
		
		fu.saveFile("bytebytebyte".getBytes(), "demo_bitmap2.txt");
		NLog.e("test_saveFile", fu.getFilePath("demo_bitmap2.txt"));
	}
}
