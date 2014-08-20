package com.sd.test.service;

import java.util.List;

import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;
import com.sd.one.model.Content;
import com.sd.one.model.response.ChannelResponse;
import com.sd.one.model.response.CommonResponse;
import com.sd.one.model.response.ContentResponse;
import com.sd.one.service.CmsAction;
import com.sd.test.BaseTestCase;

/**   
 * [A brief description]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 28, 2013    
 */
public class TestCmsAction extends BaseTestCase {

    /**
     * 测试注册
     * @throws HttpException
     */
    public void testRegist() throws HttpException{
        CmsAction mgr = new CmsAction(getContext());
        CommonResponse response = mgr.regist("username", "123", "demo@163.com");
        assertEquals(response.getSucces(), true);
    }
    
    /**
     * 测试获取栏目列表
     * @throws HttpException
     */
    public void testGetChannelList() throws HttpException{
    	 CmsAction mgr = new CmsAction(getContext());
    	 ChannelResponse response = mgr.getChannelList("1");
    	 assertEquals(response.getSucces(), true);
    }
    
    public void testGetContentList() throws HttpException{
    	 CmsAction mgr = new CmsAction(getContext());
    	 ContentResponse response = mgr.getContentList("65", 20);
    	 if(response != null){
    		 List<Content> list = response.getData().getDataList();
    		 if(!list.isEmpty()){
    			 for(Content bean : list){
    				 NLog.e("testGetContentList", bean.toString());
    			 }
    		 }
    	 }
    	 assertEquals(response.getSucces(), true);
    }
}
