/*
    ShengDao Android Client, PagerFragmentAdapter
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * [FragmentPager适配器]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-10-27
 * 
 **/
public class PagerFragmentAdapter extends FragmentPagerAdapter {

	private List<String> titles;
	private List<Fragment> fragments;
	
	public PagerFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
	
	@Override  
     public int getCount() {  
         return fragments.size();  
     }  

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
     
}
