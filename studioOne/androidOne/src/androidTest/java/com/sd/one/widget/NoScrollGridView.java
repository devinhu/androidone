package com.sd.one.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * [自定义不滚动的GridView]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-1-20
 * 
 **/
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context) {
		super(context);

	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
