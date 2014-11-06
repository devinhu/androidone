package com.sd.one.activity.demo.drawermenu;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sd.core.network.http.HttpException;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.sd.one.widget.pulltorefresh.PullToRefreshScrollView;


/**   
 * [A brief description]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 21, 2013    
 */
public class DetailActivity extends BaseActivity implements OnRefreshListener<ScrollView>{

    public static final String MAINFRAGMENT = DetailActivity.class.getSimpleName();
    public static final String PARAM_CONTENTID_KEY = "contentId";
    private final int REQ_CONTENTINFO_CODE = 1001;
    private String contentId = "342";
    private TextView tv_context;
    private PullToRefreshScrollView mPullRefreshListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_detail);
        
        contentId = getIntent().getStringExtra(PARAM_CONTENTID_KEY);
        
        mPullRefreshListView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_listview);
        mPullRefreshListView.setMode(Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(this);
        tv_context = (TextView) findViewById(R.id.tv_context);
        
        request(REQ_CONTENTINFO_CODE);
    }

    
    @Override
    public Object doInBackground(int requsetCode) throws HttpException {
//      CmsAction action = new CmsAction(mContext);
//      return action.getContentInfo(contentId);
    	return null;
    }


    @Override
    public void onSuccess(int requestCode, Object result) {
        switch(requestCode){
            case REQ_CONTENTINFO_CODE:
//                ContentInfoResponse response = (ContentInfoResponse)result;
//                if(response != null){
//                    tv_context.setText(Html.fromHtml(response.getData().getTxt()));
//                }
                mPullRefreshListView.onRefreshComplete();
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        request(REQ_CONTENTINFO_CODE);
    }
}
