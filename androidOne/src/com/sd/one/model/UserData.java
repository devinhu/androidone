package com.sd.one.model;

import com.sd.one.model.base.BaseModel;



/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class UserData extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1372855598326843553L;

    private String id;// 12,

    private String username;// androiside,

    private String email;// 2958932635@qq.com,

    private String userImg;// null,

    private String gender;// null,

    private String intro;// null,

    private String qq;// null

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

}
