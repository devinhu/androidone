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
public class ChannelData extends BaseModel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3427101908113386981L;

    private String id;// 11,

    private String path;// gnxw,

    private String lft;// 2,

    private String rgt;// 5,

    private String priority;// 10,

    private String hasContent;// true,

    private String display;// true,

    private String name;// 国内新闻,

    private List<ChannelData> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLft() {
        return lft;
    }

    public void setLft(String lft) {
        this.lft = lft;
    }

    public String getRgt() {
        return rgt;
    }

    public void setRgt(String rgt) {
        this.rgt = rgt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getHasContent() {
        return hasContent;
    }

    public void setHasContent(String hasContent) {
        this.hasContent = hasContent;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<ChannelData> getChild() {
		return child;
	}

	public void setChild(List<ChannelData> child) {
		this.child = child;
	}

}
