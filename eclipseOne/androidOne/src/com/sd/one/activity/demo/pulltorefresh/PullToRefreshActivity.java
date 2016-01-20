package com.sd.one.activity.demo.pulltorefresh;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sd.core.network.http.HttpException;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.widget.dialog.LoadDialog;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.sd.one.widget.pulltorefresh.PullToRefreshListView;

/**   
 * [A brief description]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 13, 2013    
 */
public class PullToRefreshActivity extends BaseActivity implements OnItemClickListener, OnRefreshListener2<ListView> {

    private final int REQUEST_CODE = 1001;
    
    private ArrayAdapter<String> mAdapter;
    private PullToRefreshListView mPullRefreshListView;
    private int pageno = 1;
    private List<String> list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_pulltorefresh);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_listview);
        mPullRefreshListView.setMode(Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(this);
        mPullRefreshListView.setOnItemClickListener(this);
        
        list = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mPullRefreshListView.setAdapter(mAdapter);
        
        LoadDialog.show(mContext);
        request(REQUEST_CODE);
    }


    @Override
    public Object doInBackground(int requestCode) throws HttpException {
        switch(requestCode){
            case REQUEST_CODE:
//            	CmsAction action = new CmsAction(mContext);
//                return action.getContentList("40", pageno);
        }
        return super.doInBackground(requestCode);
    }


    @Override
    public void onSuccess(int requestCode, Object result) {
        switch(requestCode){
            case REQUEST_CODE:
            	LoadDialog.dismiss(mContext);
            	if(result != null){
            		if(pageno ==1){
                        mAdapter.clear();
                    }
            		
//            		ContentResponse res = (ContentResponse)result;
//            		if(res.getSucces()){
//            			ContentData data = res.getData();
//            			for(Content bean : data.getDataList()){
//            				list.add(bean.getTitle());
//            			}
//            		}
            		
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();
            	}
            	mPullRefreshListView.onRefreshComplete();
                break;
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
    	LoadDialog.show(mContext);
    	pageno = 1;
        request(REQUEST_CODE);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
    	LoadDialog.show(mContext);
    	pageno = pageno + 1;
    	request(REQUEST_CODE);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }

}
