package com.sd.test.utils;

import com.sd.core.utils.NLog;
import com.sd.one.utils.CommonUtils;
import com.sd.test.BaseTestCase;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 * 
 **/
public class TestCommonUtils extends BaseTestCase {

	static{
		NLog.setDebug(true);
	}
	
	/**
	 * 测试SD卡是否可用
	 */
	public void testCheckSDCard(){
		boolean flag = CommonUtils.checkSDCard();
		assertEquals(flag, true);
	}
}
