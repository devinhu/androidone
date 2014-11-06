/*
    ShengDao Android Client, BaseModel
    Copyright (c) 2014 ShengDao Tech Company Limited
 */
package com.sd.one.model.base;

import java.io.Serializable;

/**
 * [model基类，主要是针对需要序列化的返回结果对象，如果返回结果需要序列化到磁盘，则返回结果中的所有对象需要继承该类实现序列化接口]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public abstract class BaseModel implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2085645529977627985L;
}
