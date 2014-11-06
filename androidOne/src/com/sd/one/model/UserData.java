/*
    ShengDao Android Client, UserData
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model;

import com.sd.one.model.base.BaseModel;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class UserData extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1372855598326843553L;

    private String id;

    private String username;

    private String email;

    private String userImg;

    private String gender;

    private String intro;

    private String qq;

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
