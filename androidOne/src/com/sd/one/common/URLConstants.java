/*
    ShengDao Android Client, URLConstants
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.common;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class URLConstants {

    /**
     * 域名
     */
    public static final String DOMAIN = "http://www.jingdl.com/jeecms/";
    
    /**
     * 用户注册
     */
    public static final String API_REGISTER_URL = "api/v1/user/register";
    /**
     * 用户登陆
     */
    public static final String API_LOGIN = "api/v1/user/login";
    /**
     * 获取用户信息
     */
    public static final String API_USER = "api/v1/user/";
    /**
     * 修改用户信息
     */
    public static final String API_MODIFYINFO = "api/v1/user/modifyInfo";
    /**
     * 修改用户密码
     */
    public static final String API_MODIFYPWD = "api/v1/user/modifyPwd";
    /**
     * 上传当前用户头像
     */
    public static final String API_UPLOADFACE = "api/v1/user/uploadFace";
    /**
     * 获取栏目列表
     */
    public static final String API_CHANNEL_LIST = "api/v1/channel/list/";
    /**
     * 获取当前栏目文章列表
     */
    public static final String API_CONTENT_LIST = "api/v1/content/list/";
    /**
     * 得到文章详细内容
     */
    public static final String API_CONTENT = "api/v1/content/";
    /**
     * 对当前文章顶一下
     */
    public static final String API_CONTENT_UP = "api/v1/content/up/";
    /**
     * 对当前文章踩一下
     */
    public static final String API_CONTENT_DOWN = "api/v1/content/down/";
    /**
     * 得到当前文章的所有的评论列表
     */
    public static final String API_COMMENT_LIST = "api/v1/comment/list/";
    /**
     * 会员发表评论
     */
    public static final String API_COMMENT_CREATE = "api/v1/comment/create";
    /**
     * 匿名发表评论
     */
    public static final String API_COMMENT_CREATENOTUSER = "api/v1/comment/createNotUser";
}
