/*
    SUNEEE Android Client, TestDateUtils
    Copyright (c) 2014 SUNEEE Tech Company Limited
*/

package com.sd.test.utils;

import com.sd.core.utils.NLog;
import com.sd.one.utils.DateStyle;
import com.sd.one.utils.DateUtils;
import com.sd.test.BaseTestCase;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-8-21
 * 
 **/
public class TestDateUtils extends BaseTestCase {

	public void test_currentDateTime(){
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD_HH_MM));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD_HH_MM_SS));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD_HH_MM));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD_HH_MM_SS));
		
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD_HH_MM_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.MM_DD_HH_MM_SS_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD_HH_MM_CN));
		NLog.e(tag, DateUtils.currentDateTime(DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
	}
}
