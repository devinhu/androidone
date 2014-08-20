package com.sd.one.model.response;

import com.sd.one.model.base.BaseResponse;


/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 * 
 **/
public class UserResponse extends BaseResponse {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5152107100891757682L;
	
	private String id;// 1,
	private String username;// admin,
	private String email;// admin@yahoo.com,
	private String userImg;// ,
	private String gender;// true,
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
