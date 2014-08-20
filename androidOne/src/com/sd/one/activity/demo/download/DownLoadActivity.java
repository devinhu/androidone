package com.sd.one.activity.demo.download;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.sd.core.network.download.DownLoadCallback;
import com.sd.core.network.download.DownloadManager;
import com.sd.core.utils.NLog;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.adapter.DownLoadAdapter;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-14
 * 
 **/
public class DownLoadActivity extends BaseActivity {

	private ListView listview;

	private DownloadManager downloadMgr;
	private DownLoadAdapter mAdapter;
	private List<DownloadInfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_layout_download);

		list = new ArrayList<DownloadInfo>();
		DownloadInfo bean = new DownloadInfo("http://images.csdn.net/20140514/20120110095325986.jpg", "task1");
		list.add(bean);

		bean = new DownloadInfo("http://images.csdn.net/20140515/iStock.jpg", "task2");
		list.add(bean);

		bean = new DownloadInfo("http://images.csdn.net/20140512/ibm-pmi-300-200-20140410.png", "task3");
		list.add(bean);

		bean = new DownloadInfo("http://images.csdn.net/20140417/gaotong-zqer-300-200-20140227.jpg", "task4");
		list.add(bean);

		bean = new DownloadInfo("http://images.csdn.net/20140514/zazhi-zd-300-200-20140228.jpg", "task5");
		list.add(bean);

		mAdapter = new DownLoadAdapter(mContext, this);
		listview = (ListView)findViewById(R.id.listview);
		listview.setAdapter(mAdapter);
		mAdapter.setList(list);
		mAdapter.notifyDataSetChanged();

		downloadMgr = DownloadManager.getInstance();
		downloadMgr.setDownLoadCallback(new DownLoadCallback() {

			@Override
			public void onLoading(String url, int bytesWritten, int totalSize) {
				super.onLoading(url, bytesWritten, totalSize);
				for (DownloadInfo bean : list) {
					if (bean.getUrl().equals(url)) {
						bean.setProgress((int) ((bytesWritten * 100) / totalSize));
					}
				}
				mAdapter.setList(list);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onSuccess(String url, String filePath) {
				super.onSuccess(url, filePath);
				NLog.e("success", "success: " + url + " filePath : " + filePath);
				for (DownloadInfo bean : list) {
					if (bean.getUrl().equals(url)) {
						bean.setState("成功");
					}
				}
				mAdapter.setList(list);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(String url, String strMsg) {
				super.onFailure(url, strMsg);
				for (DownloadInfo bean : list) {
					if (bean.getUrl().equals(url)) {
						bean.setState("失败");
					}
				}
				mAdapter.setList(list);
				mAdapter.notifyDataSetChanged();
			}
		});

		for (DownloadInfo bean1 : list) {
			downloadMgr.addHandler(bean1.getUrl());
		}
	}

	public void refresh(List<DownloadInfo> list) {
		this.list = list;
		mAdapter.setList(this.list);
		mAdapter.notifyDataSetChanged();
	}

}
