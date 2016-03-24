package com.sd.one.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sd.one.R;


/**   
 * [BaseDialog]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public abstract class BaseDialog extends Dialog implements OnClickListener{
	
    private TextView tvTitle;
    private FrameLayout mContent;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    
    private View.OnClickListener mBtn1ClickListener;
    private View.OnClickListener mBtn2ClickListener;
    private View.OnClickListener mBtn3ClickListener;
    
    private boolean isAutoDismiss1 = true;
    private boolean isAutoDismiss2 = true;
    private boolean isAutoDismiss3 = true; 
    
    /**
     * @param context
     */
    public BaseDialog(Context context) {
        super(context);
        init();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
    }

    /**
     * init method
     */
    private void init() {
        
        this.getContext().setTheme(R.style.load_dialog);
        super.setContentView(R.layout.layout_dialog);
        
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mContent = (FrameLayout) findViewById(R.id.fl_content);
        
        btn1 = (Button) findViewById(R.id.btn_confirm);
        btn2 = (Button) findViewById(R.id.btn_cancel);
        btn3 = (Button) findViewById(R.id.btn_demo);
     
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        
        Window window  = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.4f;
        
        @SuppressWarnings("deprecation")
		int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth()*0.9);
        window.setLayout(width, LayoutParams.WRAP_CONTENT); 
    }
    
    /**
     * set title
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }
    
    /**
     * set title
     * @param resId
     * @see android.app.Dialog#setTitle(int)
     */
    public void setTitle(int resId) {
        setTitle(getContext().getResources().getString(resId));
    }

    /**
     * bulid the contentview of the dialog.
     * @return
     */
    public abstract View createContentView();
    
    
    @SuppressWarnings("deprecation")
	public void setContentView(View view) {
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        view.setLayoutParams(lp);
        mContent.addView(view);
    }
    
    /**
     * set the text of the btn1
     * @param text
     */
    public void setBtn1Text(String text) {
        btn1.setText(text);
    }

    /**
     * set the text of the btn2
     * @param text
     */
    public void setBtn2Text(String text) {
        btn2.setText(text);
    }
    
    /**
     * set the text of the btn3
     * @param text
     */
    public void setBtn3Text(String text) {
        btn3.setText(text);
    }


    /**
     * set the text of the btn1
     * @param resId
     */
    public void setBtn1Text(int resId) {
        btn1.setText(resId);
    }

    /**
     * set the text of the btn2
     * @param resId
     */
    public void setBtn2Text(int resId) {
        btn2.setText(resId);
    }
    
    /**
     * set the text of the btn3
     * @param resId
     */
    public void setBtn3Text(int resId) {
        btn3.setText(resId);
    }
    
    
    public void setBottomLayoutGone(){
    	findViewById(R.id.linearlayout_bottom_btn).setVisibility(View.GONE);
    }

    /**
     * 设置点击按钮1后时候是否关闭dialog
     * 默认为true
     * @param autoDismiss
     */
    public void setAutoDismiss1(boolean autoDismiss) {
        isAutoDismiss1 = autoDismiss;
    }
    
    /**
     * 设置点击按钮2后时候是否关闭dialog
     * 默认为true
     * @param autoDismiss
     */
    public void setAutoDismiss2(boolean autoDismiss) {
        isAutoDismiss2 = autoDismiss;
    }
    
    /**
     * 设置点击按钮3后时候是否关闭dialog
     * 默认为true
     * @param autoDismiss
     */
    public void setAutoDismiss3(boolean autoDismiss) {
        isAutoDismiss3 = autoDismiss;
    }
    
    /**
     * 设置按钮1是否可见
     * @param visible
     */
    public void setBtn1Visible(boolean visible) {
        if (visible) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.GONE);
        }
    }

    /**
     * 设置按钮2是否可见
     * @param visible
     */
    public void setBtn2Visible(boolean visible) {
        if (visible) {
            btn2.setVisibility(View.VISIBLE);
        } else {
            btn2.setVisibility(View.GONE);
        }
    }
    
    /**
     * 设置按钮3是否可见
     * @param visible
     */
    public void setBtn3Visible(boolean visible) {
        if (visible) {
            btn3.setVisibility(View.VISIBLE);
        } else {
            btn3.setVisibility(View.GONE);
        }
    }
    
   /**
    * 
    *设置按钮1是否处于可点击状态
    * @param enable
    */
    public void setBtn1Enable(boolean enabled){
    	btn1.setEnabled(enabled);
    }
    
    /**
     * 
     *设置按钮2是否处于可点击状态
     * @param enable
     */
     public void setBtn2Enable(boolean enabled){
     	btn2.setEnabled(enabled);
     }
     
     /**
      * 
      *设置按钮2是否处于可点击状态
      * @param enable
      */
      public void setBtn3Enable(boolean enabled){
         btn3.setEnabled(enabled);
      }
    
    /**
     * 设置1按钮的监听
     * @param listener
     */
    public void setBtn1ClickListener(View.OnClickListener listener) {
        mBtn1ClickListener = listener;
    }

    /**
     * 设置2按钮的监听
     * @param listener
     */
    public void setBtn2ClickListener(View.OnClickListener listener) {
        mBtn2ClickListener = listener;
    }
    
    /**
     * 设置3按钮的监听
     * @param listener
     */
    public void setBtn3ClickListener(View.OnClickListener listener) {
        mBtn3ClickListener = listener;
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id) {
        case R.id.btn_confirm:
            if (mBtn1ClickListener != null) {
                mBtn1ClickListener.onClick(v);
            }
            if (isAutoDismiss1) {
                dismiss();
            }
            break;
        case R.id.btn_cancel:
            if (mBtn2ClickListener != null ) {
                mBtn2ClickListener.onClick(v);
            }
            if (isAutoDismiss2) {
                dismiss();
            }
            break;
        case R.id.btn_demo:
            if (mBtn3ClickListener != null ) {
                mBtn3ClickListener.onClick(v);
            }
            if (isAutoDismiss3) {
                dismiss();
            }
            break;   
            default:
                break;
        }
    }

}

