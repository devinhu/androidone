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
		Schema schema = new Schema(1, "com.jingdl.qulove.utils.db");

		// 添加Note对象的Schema，会生成针对Note的数据库关代码
		addCustomer(schema);

		addAddress(schema);

		addPurchase(schema);

		addProduct(schema);

		addOrder(schema);

		// C://Users/huxinwu/Desktop/demo C://Users/Administrator/Desktop/db
		// 该地址是生成的代码存放地址，相对地址不好写就写绝对地址，生成后直接拷贝到对应的地址（com.shengdao.demo.utils.db）下面
		new DaoGenerator().generateAll(schema, "/Users/devin/workspace/quaidashi/qulove/src/main/java/com/jingdl/qulove/utils/db");
	}

	/**
	 * 客户表
	 * @param schema
	 */
	private static void addCustomer(Schema schema) {
		Entity note = schema.addEntity("Customer");
		note.addIdProperty().autoincrement().primaryKey();
		note.addStringProperty("name");
		note.addStringProperty("phone");
		note.addStringProperty("cretetime");
	}

	/**
	 * 收获地址表
	 * @param schema
	 */
	private static void addAddress(Schema schema) {
		Entity note = schema.addEntity("Address");
		note.addIdProperty().autoincrement().primaryKey();
		note.addIntProperty("cid");
		note.addStringProperty("receiver");
		note.addStringProperty("mobile");
		note.addStringProperty("province");
		note.addStringProperty("city");
		note.addStringProperty("area");
		note.addStringProperty("address");
		note.addStringProperty("zipcode");
		note.addStringProperty("cretetime");
	}

	/**
	 * 采购表
	 * @param schema
	 */
	private static void addPurchase(Schema schema) {
		Entity note = schema.addEntity("Purchase");
		note.addIdProperty().autoincrement().primaryKey();
		note.addIntProperty("pid");
		note.addStringProperty("porductName");
		note.addIntProperty("basePrice");
		note.addStringProperty("image");
		note.addStringProperty("desc");
		note.addIntProperty("number");
		note.addIntProperty("price");
		note.addStringProperty("status");
		note.addBooleanProperty("planflag");
		note.addStringProperty("cretetime");
	}

	/**
	 * 产品库表
	 * @param schema
	 */
	private static void addProduct(Schema schema) {
		Entity note = schema.addEntity("Product");
		note.addIdProperty().autoincrement().primaryKey();
		note.addStringProperty("porductName");
		note.addIntProperty("basePrice");
		note.addStringProperty("image");
		note.addStringProperty("desc");
		note.addStringProperty("cretetime");
	}

	/**
	 * 订单表
	 * @param schema
	 */
	private static void addOrder(Schema schema) {
		Entity note = schema.addEntity("Order");
		note.addIdProperty().autoincrement().primaryKey();
		note.addIntProperty("cid");
		note.addStringProperty("name");
		note.addIntProperty("price");
		note.addIntProperty("finalPrice");
		note.addStringProperty("status");
		note.addStringProperty("cretetime");
	}
}
