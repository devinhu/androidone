package com.sd.one.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * [兼容ScrollView的ListView， 解决滑动冲突事件]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-1-20
 * 
 **/
public class CompaScrollerListView extends ListView {

	private ScrollView scrollView;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CompaScrollerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CompaScrollerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 */
	public CompaScrollerListView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				if(scrollView != null){
					scrollView.requestDisallowInterceptTouchEvent(true);
				}
				break;
	
			case MotionEvent.ACTION_UP:
				if(scrollView != null){
					scrollView.requestDisallowInterceptTouchEvent(false);
				}
				break;
			}
		return super.onTouchEvent(event);
	}

	public ScrollView getScrollView() {
		return scrollView;
	}

	public void setScrollView(ScrollView scrollView) {
		this.scrollView = scrollView;
	}

}
