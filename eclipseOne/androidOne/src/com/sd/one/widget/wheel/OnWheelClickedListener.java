package com.sd.one.widget.wheel;

/**   
 * Wheel clicked listener interface.
 * <p>The onItemClicked() method is called whenever a wheel item is clicked
 * <li> New Wheel position is set
 * <li> Wheel view is scrolled
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
