/*
    ShengDao Android Client, BaseTestCase
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.test;

import android.test.AndroidTestCase;

import com.sd.core.utils.NLog;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class BaseTestCase extends AndroidTestCase {

	protected final String tag = BaseTestCase.class.getSimpleName();
	
	static{
		NLog.setDebug(true);
	}
}
