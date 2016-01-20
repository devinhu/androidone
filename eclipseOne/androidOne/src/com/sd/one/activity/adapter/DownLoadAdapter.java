/*
    ShengDao Android Client, DownLoadAdapter
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sd.core.network.download.DownloadManager;
import com.sd.one.R;
import com.sd.one.activity.demo.download.DownLoadActivity;
import com.sd.one.activity.demo.download.DownloadInfo;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class DownLoadAdapter extends BaseAdapter<DownloadInfo> {

    private ViewHolder holder;
    private DownLoadActivity activity; 
    
    public DownLoadAdapter(Context context, DownLoadActivity activity) {
    	super(context);
        this.activity = activity;
    }
    
    class ViewHolder {
        TextView tv_name;
        TextView tv_state;
        Button btn_start;
        Button btn_pause;
        Button btn_continue;
        Button btn_delete;
        ProgressBar progressBar;
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.demo_layout_download_item, null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            holder.btn_pause = (Button)convertView.findViewById(R.id.btn_pause);
            holder.btn_continue = (Button)convertView.findViewById(R.id.btn_continue);
            holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
            holder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        
        DownloadInfo bean = dataSet.get(position);
        final String url = bean.getUrl();
        holder.tv_name.setText(bean.getName());
        holder.progressBar.setProgress(bean.getProgress());
        
        String state = bean.getState();
        if(!TextUtils.isEmpty(state)){
        	holder.tv_state.setText(bean.getState());
        }
        
        holder.btn_pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DownloadManager.getInstance().pauseHandler(url);
			}
		});
        
        holder.btn_continue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DownloadManager.getInstance().continueHandler(url);
			}
		});
        
        holder.btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				List<DownloadInfo> newList = new ArrayList<DownloadInfo>();
				for(DownloadInfo b : dataSet){
					if(!b.getUrl().equals(url)){
						newList.add(b);
					}
				}
				activity.refresh(newList);
				DownloadManager.getInstance().deleteHandler(url);
			}
		});
        return convertView;
    }

}
