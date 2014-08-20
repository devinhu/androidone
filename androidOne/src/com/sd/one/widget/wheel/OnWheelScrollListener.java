package com.sd.one.widget.wheel;

/**   
 * [Wheel scrolled listener interface]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public interface OnWheelScrollListener {
	/**
	 * Callback method to be invoked when scrolling started.
	 * @param wheel the wheel view whose state has changed.
	 */
	void onScrollingStarted(WheelView wheel);
	
	/**
	 * Callback method to be invoked when scrolling ended.
	 * @param wheel the wheel view whose state has changed.
	 */
	void onScrollingFinished(WheelView wheel);
}
