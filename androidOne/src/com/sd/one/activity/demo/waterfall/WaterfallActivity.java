package com.sd.one.activity.demo.waterfall;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.sd.core.network.http.HttpException;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.service.DemoAction;
import com.sd.one.widget.waterfall.internal.PLA_AdapterView;
import com.sd.one.widget.waterfall.internal.PLA_AdapterView.OnItemClickListener;
import com.sd.one.widget.waterfall.util.ImageFetcher;
import com.sd.one.widget.waterfall.view.XListView;
import com.sd.one.widget.waterfall.view.XListView.IXListViewListener;

/**   
 * [A brief description]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 13, 2013    
 */
public class WaterfallActivity extends BaseActivity implements IXListViewListener, OnItemClickListener{

    private final int REQUEST_CODE = 1001;
    private XListView mXListView;
    private ImageFetcher mImageFetcher;
    private ArrayAdapter<String> mAdapter;
    private int currentIndex = 1;
    private  String[] list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_waterfall);
        
        mXListView = (XListView)findViewById(R.id.xlistview);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);

        mImageFetcher = new ImageFetcher(mContext, 240);
        mImageFetcher.setLoadingImage(R.drawable.ic_launcher);
        mImageFetcher.setExitTasksEarly(false);
        
        list = mContext.getResources().getStringArray(R.array.main_menu_array);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mXListView.setAdapter(mAdapter);
        
        request(REQUEST_CODE);
    }

    @Override
    public Object doInBackground(int requestCode) throws HttpException {
        switch(requestCode){
            case REQUEST_CODE:
                DemoAction action = new DemoAction(mContext);
                return action.getJsonListDemo("assets://list.json");
        }
        return super.doInBackground(requestCode);
    }


    @Override
    public void onSuccess(int requestCode, Object result) {
        switch(requestCode){
            case REQUEST_CODE:
                if(currentIndex ==1){
                    mAdapter.clear();
                }
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
                mXListView.stopRefresh();
                mXListView.stopLoadMore();
                break;
        }
    }

    
    @Override
    public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onRefresh() {
        currentIndex = 1;
        request(currentIndex);
    }

    @Override
    public void onLoadMore() {
        request(currentIndex++);
    }
}
