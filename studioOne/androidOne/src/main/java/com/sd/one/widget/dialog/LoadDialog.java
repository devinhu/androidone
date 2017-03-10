package com.sd.one.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import com.sd.one.R;
import com.sd.one.utils.NToast;

/**   
 * [LoadDialog]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public class LoadDialog extends Dialog {

	/**
	 * LoadDialog
	 */
	private static LoadDialog loadDialog;
	/**
	 * canNotCancel, the dialog dimiss or undimiss flag
	 */
	private boolean canNotCancel;
	/**
	 * if the dialog don't dimiss, what is the tips.
	 */
	private String tipMsg;

	/**
	 * the LoadDialog constructor
	 * @param ctx Context
	 * @param canNotCancel boolean
	 * @param tipMsg String
	 */
	public LoadDialog(final Context ctx, boolean canNotCancel, String tipMsg) {
		super(ctx);

		this.canNotCancel = canNotCancel;
		this.tipMsg = tipMsg;
		this.getContext().setTheme(android.R.style.Theme_InputMethod);
		setContentView(R.layout.layout_dialog_loading);

		Window window = getWindow();
		WindowManager.LayoutParams attributesParams = window.getAttributes();
		attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		attributesParams.dimAmount = 0.5f;

		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (canNotCancel) {
				NToast.shortToast(getContext(), tipMsg);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
     * show the dialog
     * @param context
     */
    public static void show(Context context) {
    	show(context, null, false);
    }

    /**
     * show the dialog
     * @param context Context
     * @param message String
     */
    public static void show(Context context, String message) {
    	show(context, message, false);
    }
    
    /**
     * show the dialog
     * @param context Context
     * @param message String, show the message to user when isCancel is true. 
     * @param isCancel boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean isCancel) {
    	if (context instanceof Activity) {
	        if(((Activity) context).isFinishing()) {
	            return;
	        }
		}
    	if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
    	loadDialog = new LoadDialog(context, isCancel, message);
    	loadDialog.show();
    }
    
    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context){
    	try {
			if (context instanceof Activity) {
			    if(((Activity) context).isFinishing()) {
			    	loadDialog = null;
			        return;
			    }
			}
			
			if(loadDialog != null && loadDialog.isShowing()){
				Context loadContext = loadDialog.getContext();
				if(loadContext !=null && loadContext instanceof Activity){
					if(((Activity) loadContext).isFinishing()) {
						loadDialog = null;
				        return;
				    }
				}
				loadDialog.dismiss();
				loadDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			loadDialog = null;
		}
    }
}
