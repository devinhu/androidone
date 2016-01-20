/*
    ShengDao Android Client, BaseResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.base;

/**
 * [返回结果基类，返回结果公共字段本类实现]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class BaseResponse extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5616901114632786764L;

    private boolean succes;

    private String errorCode;

    private String errorMsg;

    public boolean getSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
