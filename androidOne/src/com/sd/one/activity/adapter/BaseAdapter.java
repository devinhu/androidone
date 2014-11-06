/*
    ShengDao Android Client, BaseAdapter
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * [适配器基类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-10-30
 * 
 **/
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

	protected List<T> dataSet;
	protected Context mContext;
	protected LayoutInflater mInflater;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseAdapter(Context context) {
		this(context, new ArrayList());
	}

	public BaseAdapter(Context context, List<T> data) {
		this.mContext = context;
		this.dataSet = data;
		mInflater = LayoutInflater.from(mContext);
	}

	public Context getContext() {
		return this.mContext;
	}

	public void addData(T data) {
		this.dataSet.add(data);
	}

	public void addData(Collection<T> data) {
		this.dataSet.addAll(data);
	}

	public void addData(int index, Collection<T> data) {
		this.dataSet.addAll(index, data);
	}

	public void removeData(Collection<T> data) {
		this.dataSet.removeAll(data);
	}

	public void removeAll() {
		this.dataSet.clear();
	}

	public void remove(T data) {
		this.dataSet.remove(data);
	}

	public void remove(int position) {
		this.dataSet.remove(position);
	}

	public List<T> subData(int index, int count) {
		return this.dataSet.subList(index, index + count);
	}

	@Override
	public int getCount() {
		return this.dataSet.size();
	}

	@Override
	public T getItem(int position) {
		return this.dataSet.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0L;
	}

	public void setItem(int position, T obj) {
		this.dataSet.set(position, obj);
	}

	public void addItem(int position, T obj) {
		this.dataSet.add(position, obj);
	}

	public void addItems(int position, Collection<T> data) {
		this.dataSet.addAll(position, data);
	}
}
