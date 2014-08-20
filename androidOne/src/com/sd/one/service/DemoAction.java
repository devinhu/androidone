package com.sd.one.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.text.TextUtils;

import com.sd.core.common.CacheManager;
import com.sd.core.common.parse.JsonMananger;
import com.sd.core.network.http.HttpException;
import com.sd.one.model.response.JSONResponse;
import com.sd.one.model.response.XMLResponse;

/**
 * [本地数据接口测试类]
 *	
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-30
 *
 **/
public class DemoAction extends BaseAction {

	/**
	 * 构造方法
	 * @param mContext
	 */
	public DemoAction(Context mContext) {
		super(mContext);
	}

	/**
	 * 获取xml内容
	 * @param url
	 * @return
	 * @throws HttpException
	 */
	public XMLResponse getXmlDemo(String url) throws HttpException{
		XMLResponse response = null;
			
	    String key = String.valueOf(url.hashCode());
	    //cache valid time 30minute
	    if(CacheManager.isInvalidCache(key, 30*60)){
	        response = CacheManager.readObject(key);
	        if(response != null){
	            return response;
	        }
	    }
	    
		String result = httpManager.get(mContext, url);
		if(!TextUtils.isEmpty(result)){
		     response = xmlToBean(result, XMLResponse.class);
		     if(response != null){
		         //add to the local cache
		         CacheManager.writeObject(response, key);
		     }
		}
		
		return response;
		
	}
	
	/**
	 * 获取json内容
	 * @param url
	 * @return
	 * @throws HttpException
	 */
	public JSONResponse getJsonDemo(String url) throws HttpException{
		JSONResponse response = null;
		
	    String key = String.valueOf(url.hashCode());
        //cache valid time 30minute
        if(CacheManager.isInvalidCache(key, 30*60)){
            response = CacheManager.readObject(key);
            if(response != null){
                return response;
            }
        }
        
		String result = httpManager.get(mContext, url);
		if(!TextUtils.isEmpty(result)){
			 response = jsonToBean(result, JSONResponse.class);
			 if(response != null){
                 //add to the local cache
                 CacheManager.writeObject(response, key);
             }
		}
		
		return response;
	}
	
	
	/**
	 * 获取json带数组内容
	 * @param url
	 * @return
	 * @throws HttpException
	 */
	public List<JSONResponse> getJsonListDemo(String url) throws HttpException{
		List<JSONResponse> response = null;
		
		String key = String.valueOf(url.hashCode());
	        //cache valid time 30minute
	        if(CacheManager.isInvalidCache(key, 30*60)){
	            response = CacheManager.readObject(key);
	            if(response != null){
	                return response;
	            }
	        }
	        
		try {
			String result = httpManager.get(mContext, url);
			if(!TextUtils.isEmpty(result)){
			   response = JsonMananger.getInstance().getJsonMapper().readValue(result, new TypeReference<List<JSONResponse>>(){});
			   if(response != null){
			       CacheManager.writeObject(response, key);
			   }
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
