/**
 * 
 */
package com.sd.test;

import android.test.AndroidTestCase;

import com.sd.core.utils.NLog;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-26
 * 
 **/
public class BaseTestCase extends AndroidTestCase {

	protected final String tag = BaseTestCase.class.getSimpleName();
	
	static{
		NLog.setDebug(true);
	}
}
