package com.sd.one.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * [自定义不滚动的ListView]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-1-20
 * 
 **/
public class NoScrollerListView extends ListView {
	
	public NoScrollerListView(Context context) {
		super(context);

	}

	public NoScrollerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
