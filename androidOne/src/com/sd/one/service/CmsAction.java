package com.sd.one.service;

import android.content.Context;
import android.text.TextUtils;

import com.sd.core.common.CacheManager;
import com.sd.core.network.http.HttpException;
import com.sd.core.network.http.RequestParams;
import com.sd.one.common.URLConstants;
import com.sd.one.model.response.ChannelResponse;
import com.sd.one.model.response.CommentResponse;
import com.sd.one.model.response.CommonResponse;
import com.sd.one.model.response.ContentInfoResponse;
import com.sd.one.model.response.ContentResponse;
import com.sd.one.model.response.LoginResponse;
import com.sd.one.model.response.UserResponse;

/**   
 * [CMS内容接口类]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 12, 2013    
 */
public class CmsAction extends BaseAction {

    /**
     * [构造方法]  
     * @param mContext
     */
    public CmsAction(Context mContext) {
        super(mContext);
    }

    /**
     * [用户注册] 
     * @param username
     * @param password
     * @param email
     * @throws HttpException
     */
    public CommonResponse regist(String username, String password, String email) throws HttpException {
        String url = getURL(URLConstants.API_REGISTER_URL);
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        
        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [用户登陆] 
     * @param username
     * @param password
     * @throws HttpException
     */
    public LoginResponse login(String username, String password) throws HttpException {
        String url = getURL(URLConstants.API_LOGIN);
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        LoginResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, LoginResponse.class);
        }
        return response;
    }
    
    /**
     * [获取用户信息] 
     * @param userId
     * @throws HttpException
     */
    public UserResponse getUser(String userId) throws HttpException {
        String url = getURL(URLConstants.API_USER, userId);
        
        UserResponse response = null;
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
        	 response = jsonToBean(result, UserResponse.class);
        }
        return response; 
    }
    
    /**
     * [修改用户信息] 
     * @param userId
     * @param email
     * @param gender
     * @param intro
     * @param qq
     * @throws HttpException
     */
    public CommonResponse modifyInfo(String userId, String email, String gender, String intro, String qq) throws HttpException {
        String url = getURL(URLConstants.API_MODIFYINFO);
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("email", email);
        params.put("gender", gender);
        params.put("intro", intro);
        params.put("qq", qq);
       
        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [修改用户密码] 
     * @param userId
     * @param password
     * @throws HttpException
     */
    public CommonResponse modifyPwd(String userId, String password) throws HttpException {
        String url = getURL(URLConstants.API_MODIFYPWD);
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("password", password);

        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [上传当前用户头像] 
     * @param userId
     * @param imgrul
     * @throws HttpException
     */
    public CommonResponse uploadFace(String userId, String imgrul) throws HttpException {
        String url = getURL(URLConstants.API_UPLOADFACE);
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("file", imgrul);
        
        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    
    /**
     * [获取栏目列表] 
     * @param parentId
     * @throws HttpException
     */
    public ChannelResponse getChannelList(String parentId) throws HttpException {
        String url = getURL(URLConstants.API_CHANNEL_LIST, parentId);

        ChannelResponse response = null;
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
        	 response = jsonToBean(result, ChannelResponse.class);
        }
        return response;
    }
    
    /**
     * [获取当前栏目文章列表] 
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @throws HttpException
     */
    public ContentResponse getContentList(String channelId, int pageNo) throws HttpException {
        return getContentList(channelId, pageNo, pageSize);
    }
    
    /**
     * [获取当前栏目文章列表] 
     * @param channelId
     * @param pageNo
     * @throws HttpException
     */
    public ContentResponse getContentList(String channelId, int pageNo, int pageSize) throws HttpException {
        String url = getURL(URLConstants.API_CONTENT_LIST, channelId, String.valueOf(pageNo), String.valueOf(pageSize));

        ContentResponse response = null;
        
        String key = String.valueOf(url.hashCode());
        //cache valid time 30minute
        if(CacheManager.isInvalidCache(key, INVALID_TIME)){
            response = CacheManager.readObject(key);
            if(response != null){
                return response;
            }
        }
        
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, ContentResponse.class);
            if(response != null){
                //add to the local cache
                CacheManager.writeObject(response, key);
            }
        }
        
        return response;
    }
    
    /**
     * [得到文章详细内容] 
     * @param contentId
     * @throws HttpException
     */
    public ContentInfoResponse getContentInfo(String contentId) throws HttpException {
        String url = getURL(URLConstants.API_CONTENT, contentId);

        ContentInfoResponse response = null;

        String key = String.valueOf(url.hashCode());
        //cache valid time 30minute
        if(CacheManager.isInvalidCache(key, INVALID_TIME)){
            response = CacheManager.readObject(key);
            if(response != null){
                return response;
            }
        }
        
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, ContentInfoResponse.class);
            if(response != null){
                //add to the local cache
                CacheManager.writeObject(response, key);
            }
        }
        
        return response;
    }
    
    /**
     * [对当前文章顶一下] 
     * @param contentId
     * @throws HttpException
     */
    public CommonResponse doUp( String contentId) throws HttpException {
        String url = getURL(URLConstants.API_CONTENT_UP, contentId);

        CommonResponse response = null;
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [对当前文章踩一下] 
     * @param contentId
     * @throws HttpException
     */
    public CommonResponse doDown(String contentId) throws HttpException {
        String url = getURL(URLConstants.API_CONTENT_DOWN, contentId);

        CommonResponse response = null;
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [得到当前文章的所有的评论列表] 
     * @param contentId
     * @param pageNo
     * @throws HttpException
     */
    public CommentResponse getCommentList(String contentId, int pageNo) throws HttpException {
        return getCommentList(contentId, pageNo, pageSize);
    }
    
    /**
     * [得到当前文章的所有的评论列表] 
     * @param contentId
     * @param pageNo
     * @param pageSize
     * @throws HttpException
     */
    public CommentResponse getCommentList(String contentId, int pageNo, int pageSize) throws HttpException {
        String url = getURL(URLConstants.API_COMMENT_LIST, contentId, String.valueOf(pageNo), String.valueOf(pageSize));

        CommentResponse response = null;
        String result = httpManager.get(mContext, url);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommentResponse.class);
        }
        return response;
    }
    
    /**
     * [会员发表评论] 
     * @param contentId
     * @param userId
     * @param text
     * @throws HttpException
     */
    public CommonResponse createComment(String contentId, String userId, String text) throws HttpException {
        String url = getURL(URLConstants.API_COMMENT_CREATE);
        RequestParams params = new RequestParams();
        params.put("contentId", contentId);
        params.put("userId", userId);
        params.put("text", text);
        
        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
    
    /**
     * [匿名发表评论] 
     * @param contentId
     * @param text
     * @throws HttpException
     */
    public CommonResponse createComment(String contentId, String text) throws HttpException {
        String url = getURL(URLConstants.API_COMMENT_CREATENOTUSER);
        RequestParams params = new RequestParams();
        params.put("contentId", contentId);
        params.put("text", text);
        
        CommonResponse response = null;
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, CommonResponse.class);
        }
        return response;
    }
}
