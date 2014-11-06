package com.sd.one.activity.demo.drawermenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sd.core.network.http.HttpException;
import com.sd.one.R;
import com.sd.one.activity.BaseFragment;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.sd.one.widget.pulltorefresh.PullToRefreshListView;


/**   
 * [A brief description]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Nov 21, 2013    
 */
public class MainFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener2<ListView>{

    public static final String MAINFRAGMENT = MainFragment.class.getSimpleName();
    public static final String PARAM_CHANNELID_KEY = "channelId";
    
    private final int REQ_CONTENT_LIST_CODE = 1001;
    
    private int pageNo = 1;
    private String channelId = "12";
    private PullToRefreshListView mPullRefreshListView;
    //private List<Content> list = new ArrayList<Content>();
    
    public MainFragment() {
        Bundle args = getArguments();
        if(args != null){
            channelId = args.getString(PARAM_CHANNELID_KEY);
        }
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.demo_layout_pulltorefresh, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        mPullRefreshListView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_refresh_listview);
        mPullRefreshListView.setMode(Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(this);
        mPullRefreshListView.setOnItemClickListener(this);
        
//        mAdapter = new MainAdapter(mContext);
//        mPullRefreshListView.setAdapter(mAdapter);
//        request(REQ_CONTENT_LIST_CODE);
    }
    
    @Override
    public Object doInBackground(int requsetCode) throws HttpException {
//        CmsAction action = new CmsAction(mContext);
//        return action.getContentList(channelId, pageNo);
        
        return null;
    }


    @Override
    public void onSuccess(int requestCode, Object result) {
        switch(requestCode){
            case REQ_CONTENT_LIST_CODE:
//                if(pageNo ==1)list.clear();
//                ContentResponse response = (ContentResponse)result;
//                if(response != null){
//                    List<Content> dataList = response.getData().getDataList();
//                    if(dataList != null){
//                        list.addAll(dataList);
//                        mAdapter.setList(list);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
                mPullRefreshListView.onRefreshComplete();
                break;
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageNo = 1;
        request(REQ_CONTENT_LIST_CODE);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageNo = pageNo + 1;
        request(REQ_CONTENT_LIST_CODE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(mContext, DetailActivity.class);
//        intent.putExtra(DetailActivity.PARAM_CONTENTID_KEY, list.get(position-1).getId());
//        startActivity(intent);
    }
}
