一个牛逼哄哄的Android框架One

One框架能帮您带来什么？
One框架分为两个项目，OneCore为核心工程，androidOne为演示项目，依赖oneCore核心。
One整个框架为MVC模式搭建，基于android framework为核心，集成Android世界中的主流技术选型，
以Pragmatic风格的Android应用参考示例，是android项目最佳实践的总结与演示。
以“复杂的世界里，一个就够了”为理念，励志帮助Android开发人员快速搭建一个简单高效的android开发框架!


异步模块：
封装EventBus类，将异步框架单独抽出来。页面通过实现回调监听获取数据并直接更新UI操作，实现多线程机制，支持并发，超过并发数需等待。
建议一般在BaseActivity、BaseFragment中实现。


HTTP请求模块：
采用第三方AsyncHttpClient方案，支持http、https方式，支持get、post、put、delete方法，支持GZIP、File格式，支持Retry、Cacel策略，堪称完美！
增加SyncHttpClient同步发送请求管理类，配合异步模块使用；这样做的好处是Action中的接口方法都可以进行单元测试。


Common模块：
页面堆栈管理ActivityPageManager：管理页面堆栈，提供完全退出方法。
缓存管理CacheManager：主要用于缓存接口返回结果，返回结果中的对象必须继承baseModel实现序列化接口，提供缓存时长方法、缓存失效方法。
系统异常处理：发布模式自动开启系统异常处理，提供友好提示，异常处理回调接口。
SharedPreferences管理：支持直接put、get对象。
LruCache管理：用于页面传大数据且不用担心释放问题。
Json解析管理：采用fastjson实现，简单粗暴。
xml解析管理：采用xstream实现，注解解析。
SoapObject解析管理：直接将soap字符串解析为java对象。


DB模块：
采用GreenDao方案，直接实现Java Object的CURD方法就可以操作数据库。 
新增DBManager类，所有数据操作只需要获取DBManager实例来获取DaoSession，然后通过DaoSession来获取你需要的所有dao即可。
新增DaoGenerator工程自动生成model、dao、session对象等代码，拷过来直接使用即可。


注解模块：集成butterknife注解框架


支付模块：集成支付宝支付


资源下载模块：
在第三方AsyncHttpClient方案增加BreakpointHttpResponseHandler类，支持多并发、多文件上传、断点续传、暂停、继续、删除下载任务。


图片下载模块：
采用universal-image-loader解决方案，有关使用情况请参见universal-image-loader官网信息，
github地址：https://github.com/nostra13/Android-Universal-Image-Loader
为了提供用户体验，建议在listview或者gridview在滑动的时候不加载图片


看到这里，估计您和您的小伙伴们都惊呆了吧！赶快动手试试吧！
具体使用请参考androidOne演示工程。

我改变不了这个世界！这个世界也休想将我改变！

如果任何问题或者建议，欢迎沟通。
QQ群：195104825
