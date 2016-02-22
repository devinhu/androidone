package com.sd.one.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.sd.one.R;

/**   
 * [MessageDialog]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public class CommonDialog extends BaseDialog {

	/** View对象 **/
	private View contentView;
	/** 能否取消 true表示不能取消，false表示可以取消 **/
	private boolean canNotCancel = false;
    
    
    /**
     * 可以取消的、带确定和取消的、默认标题的CommonDialog
     * @param context
     * @param contentView
     */
    public CommonDialog(Context context, View contentView) {
    	this(context, null, context.getString(R.string.common_confirm), null, false, contentView);
    }
    
    /**
     * 可以取消的、带确定和取消的CommonDialog
     * @param context
     * @param title
     * @param contentView
     */
    public CommonDialog(Context context, String title, View contentView) {
    	this(context, title, context.getString(R.string.common_confirm), null, false, contentView);
    }
    
    /**
     * 可以取消的CommonDialog
     * @param context
     * @param title
     * @param btnText1
     * @param btnText2
     * @param contentView
     */
    public CommonDialog(Context context, String title, String btnText1, String btnText2, View contentView) {
    	this(context, title, btnText1, btnText2, false, contentView);
    }
    
	/**
	 * CommonDialog基础构造方法
	 * @param context
	 * @param title
	 * @param btnText1
	 * @param btnText2
	 * @param canNotCancel
	 * @param contentView
	 */
	public CommonDialog(Context context, String title, String btnText1, String btnText2, boolean canNotCancel, View contentView) {
		super(context);
		
		this.canNotCancel = canNotCancel;
		this.contentView = contentView;
		
		//设置标题
		if(!TextUtils.isEmpty(title)){
			setTitle(title);
		}
		
		//设置按钮1
		if(!TextUtils.isEmpty(btnText1)){
			this.setBtn1Text(btnText1);
		}

		//设置按钮2
		if(TextUtils.isEmpty(btnText2)){
			this.setBtn2Visible(false);
		} else {
			this.setBtn2Visible(true);
			this.setBtn2Text(btnText2);
		}
		
		this.setBtn3Visible(true);
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH) {
            if (canNotCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

	@Override
	public View createContentView() {
		return contentView;
	}
}
