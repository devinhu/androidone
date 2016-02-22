package com.sd.test.db;

import java.util.Date;
import java.util.List;

import com.sd.core.utils.NLog;
import com.sd.one.utils.db.DBManager;
import com.sd.one.utils.db.Note;
import com.sd.one.utils.db.NoteDao;
import com.sd.test.BaseTestCase;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-30
 * 
 **/
public class TestDB extends BaseTestCase {

	/**
	 * 测试数据库
	 */
	public void testDB(){
		
		NoteDao dao = DBManager.getInstance(getContext()).getDaoSession().getNoteDao();
		
		//add
		for(int i=0; i<=4; i++){
			Note bean = new Note();
			bean.setComment("comment");
			bean.setDate(new Date());
			bean.setText("text");
			dao.insert(bean);
		}
		
		//udpate
		List<Note> list = dao.loadAll();
		if(!list.isEmpty()){
			Note bean = list.get(2);
			bean.setComment("comment_comment");
			dao.update(bean);
		}
		
		//query
		if(!list.isEmpty()){
			for(Note note : list){
				NLog.e("testDemo", note.getComment());
			}
		}
			
		//delete
		if(!list.isEmpty()){
			dao.delete(list.get(0));
		}
	}
}
