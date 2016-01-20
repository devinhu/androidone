/*
    ShengDao Android Client, XMLResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.response;

import com.sd.one.model.Question;
import com.sd.one.model.base.BaseModel;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * [XML返回结果演示类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
@XStreamAlias("TXN_XML")
public class XMLResponse extends BaseModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6823528118579547018L;


    @XStreamAsAttribute
    private String ErrCode;

    @XStreamAsAttribute
    private String ErrMsg;

    @XStreamAsAttribute
    private String Name;

    @XStreamAsAttribute
    private String ID;

    @XStreamAsAttribute
    private String Timestamp;

    @XStreamAsAttribute
    private String Version;

    @XStreamAsAttribute
    private String BetAccount;
    
    @XStreamAsAttribute
    private String Guid;
    
    @XStreamAsAttribute
    private String ServerId;
    
    @XStreamAsAttribute
    private String SessionId;
    
    @XStreamAsAttribute
    private String webAccount;
    
    @XStreamAlias("Question")
    private Question Question;
    
    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

	public String getBetAccount() {
		return BetAccount;
	}

	public void setBetAccount(String betAccount) {
		BetAccount = betAccount;
	}

	public String getGuid() {
		return Guid;
	}

	public void setGuid(String guid) {
		Guid = guid;
	}

	public String getServerId() {
		return ServerId;
	}

	public void setServerId(String serverId) {
		ServerId = serverId;
	}

	public String getSessionId() {
		return SessionId;
	}

	public void setSessionId(String sessionId) {
		SessionId = sessionId;
	}

	public String getWebAccount() {
		return webAccount;
	}

	public void setWebAccount(String webAccount) {
		this.webAccount = webAccount;
	}

	public Question getQuestion() {
		return Question;
	}

	public void setQuestion(Question question) {
		Question = question;
	}

	@Override
	public String toString() {
		return "XMLResponse [ErrCode=" + ErrCode + ", ErrMsg=" + ErrMsg
				+ ", Name=" + Name + ", ID=" + ID + ", Timestamp=" + Timestamp
				+ ", Version=" + Version + ", BetAccount=" + BetAccount
				+ ", Guid=" + Guid + ", ServerId=" + ServerId + ", SessionId="
				+ SessionId + ", webAccount=" + webAccount + ", Question="
				+ Question + "]";
	}
	
	
}
