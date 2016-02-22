package com.sd.one.widget.sortlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sd.one.R;

/**   
 * [SideBar]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Dec 19, 2013    
 */
public class SideBar extends View {
    
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", 
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
    // 选中
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth(); 
        // 获取每一个字母的高度
        int singleHeight = height / b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            // 重置画笔
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        // 点击y坐标
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                //setBackgroundDrawable(new ColorDrawable(0x00000000));
                setBackgroundColor(Color.TRANSPARENT);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
    
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     * @author: devin.hu
     * @version: 1.0
     * @date:   Dec 19, 2013    
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}