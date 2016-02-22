/*
    ShengDao Android Client, GuideActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.sd.core.common.PreferencesManager;
import com.sd.one.R;
import com.sd.one.activity.adapter.ViewPagerAdapter;
import com.sd.one.common.Constants;

/**
 * [新手指引页面]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-7
 * 
 **/
public class GuideActivity extends BaseActivity implements OnPageChangeListener{

	private ViewPager guidePages;
	private ArrayList<View> pageViews;
	private ImageView imageView;
	private ImageView[] imageViews;
	private ViewGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guide);
		setHeadVisibility(View.GONE);
		PreferencesManager.getInstance(mContext).put(Constants.IS_FIRST_RUN, false);
		
		LayoutInflater inflater = getLayoutInflater();
		pageViews = new ArrayList<View>();
		pageViews.add(inflater.inflate(R.layout.layout_guide_item1, null));
		pageViews.add(inflater.inflate(R.layout.layout_guide_item2, null));
		pageViews.add(inflater.inflate(R.layout.layout_guide_item3, null));
		pageViews.add(inflater.inflate(R.layout.layout_guide_item4, null));

		imageViews = new ImageView[pageViews.size()];
		guidePages = (ViewPager) findViewById(R.id.guidePages);
		group = (ViewGroup) findViewById(R.id.viewGroup);

		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(20, 0, 20, 0);
			imageViews[i] = imageView;

			// 默认选中第一张图片
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.page_indicator);
			}
			group.addView(imageViews[i]);
		}
		
		guidePages.setAdapter(new ViewPagerAdapter(pageViews));  
		guidePages.setOnPageChangeListener(this);  
	}

	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		 for (int i = 0; i < imageViews.length; i++) {  
             imageViews[position].setBackgroundResource(R.drawable.page_indicator_focused);
             if (position != i) {  
                 imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
             }  
         }
		 
		 //点击最后一页，跳转到主页
		 if(position == 3){
			 View itemView = pageViews.get(position);
			 itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
				}
			});
		 }
	} 
}
