一个牛逼哄哄的Android框架One

##One框架能帮您带来什么？

* One框架分为两个项目，OneCore为核心工程，androidOne为演示项目，依赖oneCore核心

* One整个框架为MVC模式搭建，基于android framework为核心，集成Android世界中的主流技术选型

* 以Pragmatic风格的Android应用参考示例，是android项目最佳实践的总结与演示

* 以“复杂的世界里，一个就够了”为理念，励志帮助Android开发人员快速搭建一个简单高效的android开发框架!

##异步模块： 

* 封装EventBus类，将异步框架单独抽出来。

* 支持多并发、取消操作

* 与网络模块分离实现，让接口调试更方便

* 多个请求，一个回调接口处理，让页面代码更简洁

* 建议一般在BaseActivity、BaseFragment中实现

	*  实现参考类 [AsyncTaskManager.java](https://github.com/devinhu/androidone/blob/master/oneCore/src/com/sd/core/network/async/AsyncTaskManager.java)

	*  使用参考类 [BaseActivity.java](https://github.com/devinhu/androidone/blob/master/androidOne/src/com/sd/one/activity/BaseActivity.java)


##HTTP请求模块： 

* 采用第三方AsyncHttpClient方案，支持http、https方式，支持get、post、put、delete方法，支持GZIP、File格式，支持Retry、Cacel策略，堪称完美！ 

* 改造实现SyncHttpClient，支持同步，调接口时可直接单元测试，并支持RESTFUL风格

* 改造实现BreakpointHttpResponseHandler支持多并发、多文件上传、断点续传、暂停、继续、删除下载任务

	*  实现参考类 [SyncHttpClient.java](https://github.com/devinhu/androidone/blob/master/oneCore/src/com/sd/core/network/http/SyncHttpClient.java)

	*  使用参考类 [BreakpointHttpResponseHandler.java](https://github.com/devinhu/androidone/blob/master/oneCore/src/com/sd/core/network/http/BreakpointHttpResponseHandler.java)

##Common模块： 

* 页面堆栈管理ActivityPageManager

* 各种自定义dialog

* 支持hybrid开发 

* 各种动画效果


##CacheManager缓存管理：

* 磁盘缓存，缓存对象需实现序列化接口，提供读取、失效，清除方法。一般用于对接口数据的缓存。

##系统异常处理：

* 集成BugTags，支持自动收集错误日志，在线提交测试bug。

##SharedPreferences管理：

* 支持直接put、get对象。

##LruCache管理：

* 封装LruCache，构造只缓存CACHE_SIZE大小的数量，超过CACHE_SIZE自动释放最前面的对象，建议页面传参之间使用。

##解析管理：

* 支持XML、JSON、JSOAP解析

* 一行代码轻松转JAVA对象

	*  采用fastjson实现java、json互转

	*  采用xstream实现Java、xml互转，支持注解解析

	*  自主封装，支持soapObject转Java对象

##BroadcastManager广播管理：

* 为了发送广播更加方便，自主封装了BroadcastManager，方便好用。

##DB模块： 

* 采用GreenDao方案，直接实现Java Object的CURD方法就可以操作数据库。 

* 实现DBManager，连获取Dao的代码都不用写了，不管通过DaoGenerator生成的对象如何变化，通过DBManager可以让你拿到任何Dao对象，从而实现数据库操作。

* 新增DaoGenerator工程自动生成model、dao、session对象等代码，拷过来直接使用即可。

##注解模块：

* 集成butterknife注解框架

##支付模块：

* 集成支付宝支付和微信支付

##图片下载模块： 

* 采用universal-image-loader解决方案，有关使用情况请参见[@universal-image-loader](https://github.com/nostra13/Android-Universal-Image-Loader)

* 为了提供用户体验，建议在listview或者gridview在滑动的时候不加载图片

##结语

* 看到这里，估计您和您的小伙伴们都惊呆了吧！赶快动手试试吧！

* 具体使用请参考androidOne演示工程。

* 我改变不了这个世界！这个世界也休想将我改变！

* 如果任何问题或者建议，欢迎沟通。

* QQ群：195104825
