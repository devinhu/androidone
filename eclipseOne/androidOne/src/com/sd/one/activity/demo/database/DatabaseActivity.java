/*
    SUNEEE Android Client, DatabaseActivity
    Copyright (c) 2014 SUNEEE Tech Company Limited
*/

package com.sd.one.activity.demo.database;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sd.core.utils.NLog;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.utils.db.DBManager;
import com.sd.one.utils.db.Note;
import com.sd.one.utils.db.NoteDao;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-8-20
 * 
 **/
public class DatabaseActivity extends BaseActivity implements OnClickListener{

	private NoteDao dao;
	private Button btn_request;
	private Button btn_download;
	private Button btn_database;
	private Button btn_query;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_layout_database);
		
		btn_request = (Button)findViewById(R.id.btn_request);
		btn_request.setOnClickListener(this);
		btn_download = (Button)findViewById(R.id.btn_download);
		btn_download.setOnClickListener(this);
		btn_database = (Button)findViewById(R.id.btn_database);
		btn_database.setOnClickListener(this);
		btn_query = (Button)findViewById(R.id.btn_query);
		btn_query.setOnClickListener(this);
		
		dao = DBManager.getInstance(mContext).getDaoSession().getNoteDao();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_request:
				for(int i=0; i<=4; i++){
					Note bean = new Note();
					bean.setComment("comment");
					bean.setDate(new Date());
					bean.setText("text");
					dao.insert(bean);
				}
				break;
				
			case R.id.btn_download:
				List<Note> list = dao.loadAll();
				if(!list.isEmpty()){
					for(Note note : list){
						NLog.e("testDemo", note.getComment());
					}
				}
				
				if(!list.isEmpty()){
					dao.delete(list.get(0));
				}
				break;
				
			case R.id.btn_database:
				list = dao.loadAll();
				if(!list.isEmpty()){
					for(Note note : list){
						note.setComment("update" + System.currentTimeMillis());
						dao.update(note);
					}
				}
				break;
				
			case R.id.btn_query:
				list = dao.loadAll();
				if(!list.isEmpty()){
					for(Note note : list){
						NLog.e("testDemo", note.getComment());
					}
				}
				break;
		}
	}
}
