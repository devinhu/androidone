package com.sd.one.widget.sortlistview;

import java.util.Comparator;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-7
 * 
 **/
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
