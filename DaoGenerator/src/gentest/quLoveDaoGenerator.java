/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class quLoveDaoGenerator {

	public static void main(String[] args) throws Exception {
		// com.shengdao.demo.utils.db是生成的类package的值
		Schema schema = new Schema(3, "com.jingdl.qulove.utils.db");

		// 添加Note对象的Schema，会生成针对Note的数据库相关代码
		addFriend(schema);

		addNotice(schema);

		// C://Users/huxinwu/Desktop/demo C://Users/Administrator/Desktop/db
		// 该地址是生成的代码存放地址，相对地址不好写就写绝对地址，生成后直接拷贝到对应的地址（com.shengdao.demo.utils.db）下面
		new DaoGenerator().generateAll(schema, "C://Users/Administrator/Desktop/db");
	}

	/**
	 * @param schema
	 */
	private static void addFriend(Schema schema) {
		Entity note = schema.addEntity("Friend");
		note.addStringProperty("userId").notNull().primaryKey();
		note.addStringProperty("uesrName");
		note.addStringProperty("portraitUri");
	}

	/**
	 * @param schema
	 */
	private static void addNotice(Schema schema) {
		Entity note = schema.addEntity("Notice");
		note.addIdProperty().autoincrement().primaryKey();
		note.addStringProperty("pushContent");
		note.addStringProperty("targetid");
		note.addStringProperty("time");
	}

}
