package com.sd.one.widget.wheel.adapter;

/**   
 * [Wheel adapter interface]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013   
 * @deprecated Use WheelViewAdapter 
 */
public interface WheelAdapter {
	/**
	 * Gets items count
	 * @return the count of wheel items
	 */
	public int getItemsCount();
	
	/**
	 * Gets a wheel item by index.
	 * 
	 * @param index the item index
	 * @return the wheel item text or null
	 */
	public String getItem(int index);
	
	/**
	 * Gets maximum item length. It is used to determine the wheel width. 
	 * If -1 is returned there will be used the default wheel width.
	 * 
	 * @return the maximum item length or -1
	 */
	public int getMaximumLength();
}
