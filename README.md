一个牛逼哄哄的Android框架One

##One框架能帮您带来什么？

* One框架分为两个项目，OneCore为核心工程，androidOne为演示项目，依赖oneCore

* One整个框架为MVC模式搭建，基于android framework为核心，集成Android世界中的主流技术选型

* 以Pragmatic风格的Android应用参考示例，是android项目最佳实践的总结与演示

* 以“复杂的世界里，一个就够了”为理念，励志帮助Android开发人员快速搭建一个简单高效的android开发框架!


##异步模块 

* 封装EventBus类，将异步框架单独抽出来，任何耗时操作（不仅仅是网络请求）都可以放到异步模块里

* 与网络模块分离实现，可以直接写单元测试类测试接口，让接口调试更方便

* 支持多并发、取消操作

* 多个请求，一个回调接口处理，让页面代码更简洁

* 建议一般在BaseActivity、BaseFragment中实现

	*  实现参考类 [AsyncTaskManager.java](https://github.com/devinhu/androidone/blob/master/studioOne/oneCore/src/main/java/com/sd/core/network/async/AsyncTaskManager.java)

	*  使用参考类 [BaseActivity.java](https://github.com/devinhu/androidone/blob/master/studioOne/androidOne/src/main/java/com/sd/one/activity/BaseActivity.java)

	*  使用参考类 [BaseFragment.java](https://github.com/devinhu/androidone/blob/master/studioOne/androidOne/src/main/java/com/sd/one/activity/BaseFragment.java)


##HTTP请求模块 

* 采用第三方AsyncHttpClient方案，支持http、https方式，支持get、post、put、delete方法，支持GZIP、File格式，支持Retry、Cacel策略，堪称完美！ 

* 改造实现SyncHttpClient，支持同步，并支持RESTFUL风格，调接口时可直接单元测试

	*  实现参考类 [SyncHttpClient.java](https://github.com/devinhu/androidone/blob/master/studioOne/oneCore/src/main/java/com/sd/core/network/http/SyncHttpClient.java)


##DownloadManager资源下载模块

* 改造实现BreakpointHttpResponseHandler支持多并发、多文件上传、断点续传、暂停、继续、删除下载任务

```javascript
	/**
	 * [下载器管理类，支持并发、暂停、继续、删除任务操作以及断点续传]
	 * 
	DownloadManager downloadMgr = DownloadManager.getInstance();
	downloadMgr.setDownLoadCallback(new DownLoadCallback(){
		
		@Override
		public void onLoading(String url, int bytesWritten, int totalSize) {
			super.onLoading(url, bytesWritten, totalSize);
		}
		
		@Override
		public void onSuccess(String url) {
			super.onSuccess(url);
		}
		
		@Override
		public void onFailure(String url, String strMsg) {
			super.onFailure(url, strMsg);
		}
	});
	
	//添加下载任务
	downloadMgr.addHandler(url);
	* 
	**/
```

##BluetoothManager蓝牙处理模块

```javascript
	/**
	 * [蓝牙管理类]
	 * 
	 */
	BluetoothManager bluetoothManager = BluetoothManager.getInstance(new BluetoothCallBack(){
		@Override
		public void onStateChange(int bluetoothState, String message) {
			switch(bluetoothState){
				//蓝牙不可用
				case BluetoothService.STATE_UNAVAILABLE:
					NToast.shortToast(mContext, "蓝牙不可用");
					break;
					
				//蓝牙未连接
				case BluetoothService.STATE_NONE:
					NToast.shortToast(mContext, "蓝牙未连接");
					break;
					
				//蓝牙空闲
				case BluetoothService.STATE_LISTEN:
					break;
					
				//蓝牙正连接
				case BluetoothService.STATE_CONNECTING:
					NToast.shortToast(mContext, "蓝牙正连接");
					break;
					
				//蓝牙已连接, 当如果连接上了，message就是蓝牙的名称
				case BluetoothService.STATE_CONNECTED:
					NToast.shortToast(mContext, "蓝牙已连接");
					mBluetoothState = true;
					break;
			}
		}

		@Override
		public void onResult(int requsetCode, String data) {
			//回调结果在页面显示
			
		}
	});

	//发送蓝牙请求
	bluetoothManager.request(SEND_INL_CODE, charStr);

	//断开
	bluetoothManager.stop();

```

##Common模块

* 页面堆栈管理ActivityPageManager

* 各种自定义dialog

* 支持hybrid开发 

* 各种工具类

* 各种动画效果


##SharedPreferences管理

* 支持直接put、get对象。


##LruCache管理

* 封装LruCache，只缓存CACHE_SIZE大小的数量，超过CACHE_SIZE自动释放前面的对象，建议页面间传参使用。


##Exception系统异常处理

* Bugtags是新一代的、专为移动测试而生的缺陷发现及管理工具。移动App集成Bugtags SDK后，测试人员就可以直接在App里所见即所得的提交 bug，SDK会自动截屏、收集App运行时数据，如：设备信息，控制台数据，用户的操作步骤等，团队人员在Bugtags云端高效的跟踪及管理bug。


##Parse解析管理

* 支持XML、JSON、JSOAP解析

* 一行代码轻松转JAVA对象

	*  采用fastjson实现java、json互转

	*  采用xstream实现Java、xml互转，支持注解解析

	*  自主封装，支持soapObject转Java对象


##CacheManager缓存管理

* 磁盘缓存，缓存对象需实现序列化接口，提供读取、失效，清除方法。一般用于对接口数据的缓存。

```javascript
   /**
     * 缓存使用示例
     * 
     * @return
     * @throws HttpException
     */
    public AdResponse getAdList() throws HttpException {
    	AdResponse response = null;
    	
        RequestParams params = getRequestParams();
        params.put("method", "fmms.getAdvertisementList");
        params.put("data", "{}");
        
        //根据请求得到唯一的缓存Key
        String key = getCacheKey(AdResponse.class.getSimpleName());
        
        //读取缓存
        if(CacheManager.isInvalidCache(key, INVALID_TIME_1DAY)){
        	response = CacheManager.readObject(key);
        	if(response != null && response.isSuccess()){
        		 return response;
        	}
        }
        
        String result = httpManager.post(mContext, Constants.DOMAIN, getSignParams(params), ContentType);
        if(!TextUtils.isEmpty(result)){
        	//一句话解析成对象
            response = jsonToBean(result, AdResponse.class);
            if(response != null && response.isSuccess()){
            	 //获取数据成功，写入缓存
            	CacheManager.writeObject(response, key);
            }
        }
        
        //最后都没有数据，还是从缓存中取
        if(response == null){
        	response = CacheManager.readObject(key);
        	if(response != null && response.isSuccess()){
        		return response;
        	}
        }
        
        return response;
    }
```

##BroadcastManager广播管理

* 为了发送广播更加方便，自主封装了BroadcastManager，方便好用。

```javascript
	/**
	 * [BroadcastManager使用示例]
	 * 
	//在任何地方发送广播
	BroadcastManager.getInstance(mContext).sendBroadcast(FindOrderActivity.ACTION_RECEIVE_MESSAGE);
	 
	//页面在oncreate中初始化广播
	BroadcastManager.getInstance(mContext).addAction(ACTION_RECEIVE_MESSAGE, new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String command = intent.getAction();
			if(!TextUtils.isEmpty(command)){
				if((ACTION_RECEIVE_MESSAGE).equals(command)){
					//获取json结果
					String json = intent.getStringExtra("result");
					//做你该做的事情
				}
			}
		}
	});
    
	//页面在ondestory销毁广播
	BroadcastManager.getInstance(mContext).destroy(ACTION_RECEIVE_MESSAGE);
	* 
	**/
```

##DB模块

* 采用[GreenDao](http://greendao-orm.com/)ORM方案，直接实现Java Object的CURD方法就可以操作数据库，非常好用，极力推荐。 

* 实现DBManager，连获取Dao的代码都不用写了，不管通过DaoGenerator生成的对象如何变化，通过DBManager可以让你拿到任何Dao对象，从而实现数据库操作。

* [DaoGenerator](https://github.com/devinhu/androidone/tree/master/DaoGenerator)工程自动生成model、dao、session对象等代码，拷过来直接使用即可。

```javascript
	/**
	 * 数据库示例
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

```

##注解模块

* 集成[butterknife](http://jakewharton.github.io/butterknife/)注解框架，一个No Magic的Android注入框架，用过的人都说好，极力推荐。


##支付模块

* 集成支付宝支付和微信支付

	* 支付宝示例：客户端封装参数，调用支付宝
	```javascript
		String PARTNER = "2088XXXXXXXXXXXX";
		String SELLER = "demo@yahoo.cn";
		String RSA_PRIVATE = "私钥";
		PayUtils payutils = new PayUtils(activity, PARTNER, SELLER, RSA_PRIVATE, "服务器回调订单地址[异步处理]");
		payutils.setPayListener(new PayListener() {
			@Override
			public void onPayResult(int state, String message) {
				
			}
		});
		
		try {
			payutils.pay("测试商品", "测试商品详情", "0.01", "唯一订单号，服务器生成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	```

	* 支付宝示例：服务器封装所有的参数，返回url直接调用支付宝
	```javascript
		PayUtils1 payutils= new PayUtils1(BespeakActivity.this, new PayListener() {
			@Override
			public void onPayResult(int state, String message) {
				switch (state) {
					case PayUtils1.PAY_SUC_CODE:
						MessageDialog dialog = new MessageDialog(mContext, getString(R.string.bespeak_order_suc));
						dialog.setConfirmListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								BespeakActivity.this.finish();
							}
						});
						dialog.show();
						break;
						
					case PayUtils1.PAY_DOING_CODE:
					case PayUtils1.PAY_GOODS_CODE:
					case PayUtils1.PAY_INIT_CODE:
					case PayUtils1.PAY_FAIL_CODE:
						NToast.shortToast(mContext, message);
						break;
				}
			}
		});
		payutils.pay(res.getData().getUrl());
	```

	* 微信支付示例
	```javascript
		PayReq payReq = res.getData().getPayReq();
		payReq.packageValue = "Sign=WXPay";
		IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(Constants.WEIXIN_APP_ID);
		msgApi.sendReq(payReq);
	```

##图片下载模块

* 采用universal-image-loader解决方案，有关使用情况请参见[universal-image-loader](https://github.com/nostra13/Android-Universal-Image-Loader)

* 为了提供用户体验，建议在listview或者gridview在滑动的时候不加载图片
```javascript
refreshlistview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
```

##日志

* NLog日志输出类
* config.properties文件(assets文件夹下)配置日志开关
```javascript
#is debug mode, if debug is true that log is open, if debug is false that log is close.
debug=true
```


##教程
* [优酷视频教程地址](http://v.youku.com/v_show/id_XMTQwNTU3NjI4NA==.html?qq-pf-to=pcqq.c2c)，非常感谢[融云阿明](https://github.com/devinhu/SeaStar)的辛苦录制！
* [PPT手把手教程地址](https://github.com/devinhu/androidone/tree/master/androidOne%E5%BF%AB%E9%80%9F%E5%BC%80%E5%8F%91%E6%A1%86%E6%9E%B6ppt)

##常用网址推荐
[Android开发技术周报](http://www.androidweekly.cn/)

[http://www.androiddevtools.cn/](http://www.androiddevtools.cn/)

[android-studio中文站](http://www.android-studio.org/index.php)


##结语

* 看到这里，估计您和您的小伙伴们都惊呆了吧！赶快动手试试吧！

* 具体使用请参考androidOne演示工程。

* 我改变不了这个世界！这个世界也休想将我改变！

* 如果任何问题或者建议，欢迎沟通。

* QQ群：195104825
