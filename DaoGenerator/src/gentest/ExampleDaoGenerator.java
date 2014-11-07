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
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
    	//com.shengdao.demo.utils.db是生成的类package的值
        Schema schema = new Schema(3, "com.shengdao.demo.utils.db");

        //添加Note对象的Schema，会生成针对Note的数据库相关代码
        addNote(schema);
        
        //添加Channel对象的Schema，会生成针对Channel的数据库相关代码
        addChannel(schema);
        
        //添加ChannelItem对象的Schema，会生成针对ChannelItem的数据库相关代码
        addChannelItem(schema);

        //C://Users/huxinwu/Desktop/demo 该地址是生成的代码存放地址，相对地址不好写就写绝对地址，生成后直接拷贝到对应的地址（com.shengdao.demo.utils.db）下面
        new DaoGenerator().generateAll(schema, "C://Users/huxinwu/Desktop/demo");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
    	Entity note = schema.addEntity("Friend");
    	note.addStringProperty("userId").notNull().primaryKey();
    	note.addStringProperty("name");
    	note.addStringProperty("portraitUri");
    }
    
    /**
     * @param schema
     */
    private static void addChannel(Schema schema) {
    	Entity note = schema.addEntity("Channel");
    	note.addStringProperty("id").notNull().primaryKey();
    	note.addStringProperty("title");
    	note.addStringProperty("image");
    	note.addStringProperty("is_show");
    	note.addStringProperty("chan_seq");
    	note.addStringProperty("link");
    	note.addStringProperty("type");
    }
    
    /**
     * @param schema
     */
    private static void addChannelItem(Schema schema) {
    	Entity note = schema.addEntity("ChannelItem");
    	note.addLongProperty("id").notNull().primaryKey();
    	note.addStringProperty("name");
    	note.addIntProperty("channel");
    	note.addIntProperty("orderId");
    	note.addIntProperty("selected");
    	note.addStringProperty("type");
    	note.addStringProperty("link");
    	note.addStringProperty("imgSource");
    	note.addBooleanProperty("downloadCnt");
    	note.addBooleanProperty("isSelected");
    	note.addBooleanProperty("isAttention");
    }

    @SuppressWarnings("unused")
	private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }

}
