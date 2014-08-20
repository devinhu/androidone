/*
    Launch Android Client, ViewPagerAdapter
    Copyright (c) 2014 LAUNCH Tech Company Limited
    http:www.cnlaunch.com
*/

package com.sd.one.activity.adapter;

import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-5-15
 * 
 **/
public class ViewPagerAdapter extends PagerAdapter {

	private ArrayList<View> pageViews;
	
	 /**
	 * @param pageViews
	 */
	public ViewPagerAdapter(ArrayList<View> pageViews) {
		this.pageViews = pageViews;
	}

	@Override  
     public int getCount() {  
         return pageViews.size();  
     }  

     @Override  
     public boolean isViewFromObject(View arg0, Object arg1) {  
         return arg0 == arg1;  
     }  

     @Override  
     public int getItemPosition(Object object) {  
         return super.getItemPosition(object);  
     }  

     @Override  
     public void destroyItem(View arg0, int arg1, Object arg2) {  
         ((ViewPager) arg0).removeView(pageViews.get(arg1));  
     }  

     @Override  
     public Object instantiateItem(View arg0, int arg1) {  
         ((ViewPager) arg0).addView(pageViews.get(arg1));  
         return pageViews.get(arg1);  
     }  

     @Override  
     public void restoreState(Parcelable arg0, ClassLoader arg1) {  

     }  

     @Override  
     public Parcelable saveState() {  
         return null;  
     }  

     @Override  
     public void startUpdate(View arg0) {  

     }  

     @Override  
     public void finishUpdate(View arg0) {  

     }  
}
