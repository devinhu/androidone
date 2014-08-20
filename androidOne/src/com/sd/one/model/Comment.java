package com.sd.one.model;

import com.sd.one.model.base.BaseModel;


/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class Comment extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5549027190301381652L;
    

    private String id;// 22,

    private String createTime;// 1384312004000,

    private String replayTime;// null,

    private String ups;// 0,

    private String downs;// 0,

    private String recommend;// false,

    private String ip;// 10.106.97.122,

    private String reply;// null,

    private String text;// test12345678,

    private String replayUser;// null,

    private String commentUser;// null

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(String replayTime) {
        this.replayTime = replayTime;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public String getDowns() {
        return downs;
    }

    public void setDowns(String downs) {
        this.downs = downs;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReplayUser() {
        return replayUser;
    }

    public void setReplayUser(String replayUser) {
        this.replayUser = replayUser;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

}
