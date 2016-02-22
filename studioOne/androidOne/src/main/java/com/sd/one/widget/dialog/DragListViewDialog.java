package com.sd.one.widget.dialog;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.sd.one.R;
import com.sd.one.widget.draglistview.DragSortListView;
import com.sd.one.widget.draglistview.DragSortListView.DropListener;
import com.sd.one.widget.draglistview.DragSortListView.RemoveListener;

/**
 * [A brief description]
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date: Oct 28, 2013
 */
public class DragListViewDialog extends BaseDialog implements DropListener, RemoveListener{

	/**
	 * contentView
	 */
	private LinearLayout contentView;
	private DragSortListView listview;
	private ArrayAdapter<String> adapter;

	public DragListViewDialog(Context context) {
		super(context);
		contentView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_drag, null);
		listview = (DragSortListView) contentView.findViewById(R.id.listview);
		
		String[] array = context.getResources().getStringArray(R.array.jazz_artist_names);
	    ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

	    adapter = new ArrayAdapter<String>(context, R.layout.layout_dialog_drag_item, R.id.text, arrayList);
	    listview.setAdapter(adapter);
	        
	    listview.setDropListener(this);
	    listview.setRemoveListener(this);
	}

	@Override
	public View createContentView() {
		return contentView;
	}
	
	@Override
	public void remove(int which) {
		String item = adapter.getItem(which);
		adapter.remove(item);
		listview.removeCheckState(which);
	}

	@Override
	public void drop(int from, int to) {
		if (from != to) {
			String item = adapter.getItem(from);
			adapter.remove(item);
			adapter.insert(item, to);
			listview.moveCheckState(from, to);
		}
	}
}
