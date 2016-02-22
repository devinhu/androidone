package com.sd.one.widget.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sd.one.R;

/**   
 * [MenuDialog class]
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 10, 2013    
 */
public class MenuDialog extends BaseDialog {

	/**
	 * the logger object
	 */
	public static final String TAG = MenuDialog.class.getSimpleName();
	/**
	 * text gravity left
	 */
	public static final int GRAVITY_LEFT = 0;
	/**
	 * text gravity center
	 */
	public static final int GRAVITY_CENTER = 1;
	/**
	 * text default gravity
	 */
	private int mTextGravity = GRAVITY_LEFT;
	/**
	 * contentView
	 */
	private LinearLayout contentView;
	/**
	 * llMenusContainer
	 */
	private LinearLayout llMenusContainer;
	/**
	 * onMenuItemClickListener
	 */
	private OnMenuItemClickListener onMenuItemClickListener;

	/**
	 * MenuDialog constructor
	 * @param context Context
	 */
	public MenuDialog(Context context) {
		super(context);
		contentView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_menu, null);
		llMenusContainer = (LinearLayout) contentView.findViewById(R.id.ll_menus_container);
		setBtn1Visible(false);
		setBtn1Text(R.string.common_cancel);
	}

	/**
	 * MenuDialog constructor
	 * @param context Context
	 * @param menus String[]
	 */
	public MenuDialog(Context context, String[] menus) {
		this(context);
		setMenus(menus);
	}

	/**
	 * MenuDialog constructor
	 * @param context Context
	 * @param menus List<String>
	 */
	public MenuDialog(Context context, List<String> menus) {
		this(context);
		setMenus(menus);
	}

	/**
	 * MenuDialog constructor 
	 * @param context Context
	 * @param menus String[]
	 * @param textGravity int
	 */
	public MenuDialog(Context context, String[] menus, int textGravity) {
		this(context);
		setMenus(menus, textGravity);
	}

	/**
	 * MenuDialog constructor
	 * @param context Context
	 * @param menus List<String>
	 * @param textGravity int
	 */
	public MenuDialog(Context context, List<String> menus, int textGravity) {
		this(context);
		setMenus(menus, textGravity);
	}

	@Override
	public View createContentView() {
		return contentView;
	}

	/**
	 * set the menus
	 * @param menus List<String>
	 * @param textGravity int
	 */
	public void setMenus(List<String> menus, int textGravity) {
		setMenus(menus, textGravity, null);
	}

	/**
	 * setMenus 
	 * @param menus List<String>
	 * @param textGravity int
	 * @param isHides List<Boolean>
	 */
	public void setMenus(List<String> menus, int textGravity, List<Boolean> isHides) {
		if (textGravity == GRAVITY_LEFT) {
			mTextGravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
		} else {
			mTextGravity = Gravity.CENTER;
		}

		llMenusContainer.removeAllViews();
		if (menus != null) {
			for (int i = 0; i < menus.size(); i++) {
				TextView tvMenu = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_menu_item, null);
				tvMenu.setGravity(mTextGravity);
				tvMenu.setText(menus.get(i));
				final int position = i;
				tvMenu.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (onMenuItemClickListener != null) {
							onMenuItemClickListener.onMenuItemClick(position);
						}
						dismiss();
					}
				});
				llMenusContainer.addView(tvMenu);
			}
		}

		if (isHides != null) {
			for (int i = 0; i < isHides.size(); i++) {
				View menuView = llMenusContainer.getChildAt(i);
				if (isHides.get(i)) {
					menuView.setVisibility(View.GONE);
				} else {
					menuView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	/**
	 * setMenus
	 * @param menus String[]
	 * @param textGravity int
	 */
	public void setMenus(String[] menus, int textGravity) {
		List<String> listMenus = new ArrayList<String>();
		for (int i = 0; i < menus.length; i++) {
			listMenus.add(menus[i]);
		}
		setMenus(listMenus, textGravity, null);
	}

	/**
	 * setMenus
	 * @param menus List<String>
	 */
	public void setMenus(List<String> menus) {
		setMenus(menus, GRAVITY_LEFT);
	}

	/**
	 * setMenus
	 * @param menus String[]
	 */
	public void setMenus(String[] menus) {
		setMenus(menus, GRAVITY_LEFT);
	}

	/**
	 * setOnMenuItemClickListener 
	 * @param onMenuItemClickListener OnMenuItemClickListener
	 */
	public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	/**   
	 * [OnMenuItemClickListener class]
	 * @author: devin.hu
	 * @version: 1.0
	 * @date:   Oct 10, 2013    
	 */
	public interface OnMenuItemClickListener {
		/**
		 * onMenuItemClick
		 * @param position int
		 */
		void onMenuItemClick(int position);
	}
}
