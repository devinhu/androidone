package com.sd.one.widget.wheel.adapter;

import android.content.Context;

/**   
 * @param <T> the element type
 * [The simple Array wheel adapter]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {
    
    /**
     * items
     */
    private T items[];

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayWheelAdapter(Context context, T items[]) {
        super(context);
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }
    
    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }
}
