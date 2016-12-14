package com.sd.one.model.response;


import com.sd.one.model.base.BaseModel;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/5/5
 */
public class ConfigData extends BaseModel {

    private String id;
    private String configName;
    private int type;//类型 2个性标签 3满意部位 4感情状态 5性取向 6举报类型 7投诉类型 8星座
    private String orderNum;
    private String description;
    private String valid;//是否可用 0可用 1不可用
    private String configCode;
    private String configValue;
    private boolean checkFlag;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String toString() {
        return "ConfigData{" +
                "id='" + id + '\'' +
                ", configName='" + configName + '\'' +
                ", type=" + type +
                ", orderNum='" + orderNum + '\'' +
                ", description='" + description + '\'' +
                ", valid='" + valid + '\'' +
                ", configCode='" + configCode + '\'' +
                ", configValue='" + configValue + '\'' +
                ", checkFlag=" + checkFlag +
                '}';
    }
}
