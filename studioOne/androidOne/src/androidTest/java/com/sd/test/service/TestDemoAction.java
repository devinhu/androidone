/*
    ShengDao Android Client, TestDemoAction
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.test.service;

import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;
import com.sd.one.model.Area;
import com.sd.one.model.response.GetAreaResponse;
import com.sd.one.service.DemoAction;
import com.sd.test.BaseTestCase;

/**
 * [DemoAction测试类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class TestDemoAction extends BaseTestCase {

   public void getArea() throws HttpException{
	   DemoAction action = new DemoAction(getContext());
	   GetAreaResponse res = action.getArea();
	   if(res != null && res.getData() != null){
		   for(Area bean : res.getData()){
			   NLog.e(tag, bean.toString());
		   }
	   }
   }
}
