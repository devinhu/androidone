package com.sd.one.model;

import java.util.List;

import com.sd.one.model.base.BaseModel;



/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Nov 25, 2013
 */
public class ContentData extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 767204620293226478L;

    private String totalCount;

    private String totalPage;

    private String isLastPage;

    private List<Content> dataList;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(String isLastPage) {
        this.isLastPage = isLastPage;
    }

    public List<Content> getDataList() {
        return dataList;
    }

    public void setDataList(List<Content> dataList) {
        this.dataList = dataList;
    }

}
