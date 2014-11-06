One框架能帮您带来什么？
 
One整个框架为MVC模式搭建，以android framework为核心框架，在之上集成当前各种流行的技术而形成的一个开源android模板工程，帮助Android开发人员在项目开发的时候只需关注项目本身的业务逻辑，项目中常用的模块以及组件则拾之即可。
 
异步模块：
封装AsyncTask类，将异步框架单独抽出来。页面通过实现回调监听获取数据并直接更新UI操作，实现多线程机制，支持并发，超过并发数需等待。建议一般在BaseActivity、BaseFragment中实现。
 
使用如下：
在BaseActivity类onCreate方法初始化：
mAsyncTaskManager = AsyncTaskManager.getInstance(mContext);
 
实现如下方法：
 /**
 * 发送请求（需要检查网络）
 * @param requsetCode 请求码
 */
public void request(int requsetCode){
    mAsyncTaskManager.request(requsetCode, this);
}
 
/**
 * 发送请求
 * @param requsetCode 请求码
 * @param isCheckNetwork 是否需检查网络，true检查，false不检查
 */
public void request(int requsetCode, boolean isCheckNetwork){
    mAsyncTaskManager.request(requsetCode, isCheckNetwork, this);
}
 
/**
 * 取消请求
 * @param requsetCode
 */
public void cancelRequest(int requsetCode){
    mAsyncTaskManager.cancelRequest(requsetCode);
}
 
/**
 * 取消所有请求
 */
public void cancelRequest(){
    mAsyncTaskManager.cancelRequest();
}
 
@Override
public Object doInBackground(int requestCode) throws HttpException{
    return null;
}
 
@Override
public void onSuccess(int requestCode, Object result) {
 
}
 
@Override
public void onFailure(int requestCode, int state, Object result) {
    switch(state){
        //网络不可用给出提示
        case AsyncTaskManager.HTTP_NULL_CODE:
            break;
 
        //网络有问题给出提示
         case AsyncTaskManager.HTTP_ERROR_CODE:
            break;
 
        //请求有问题给出提示
        case AsyncTaskManager.REQUEST_ERROR_CODE:
            break;
    }    
}
HTTP请求模块：
采用第三方AsyncHttpClient方案，在此基础上增加同步发送请求管理类（配合异步框架使用），支持http、https方式，支持get、post、put、delete方法，支持GZIP、File格式，支持Retry、Cacel策略，堪称完美！
 
Common模块：
页面堆栈管理，缓存管理、系统异常处理、SharedPreferences管理、LruCache管理（用于页面传大数据且不用担心释放问题）、Json解析管理对象、xml解析管理对象、SoapObject解析管理对象。
 
DB模块：
采用GreenDao方案，直接实现Java Object的CURD方法就可以操作数据库。 新增java工程自动生成model、dao、session对象等代码，拷过来直接使用即可。新增DBManager类，所有数据操作只需要获取DBManager实例来获取DaoSession，然后通过DaoSession来获取你需要的dao即可。

使用如下：
dao = DBManager.getInstance(mContext).getDaoSession().getNoteDao();
 
资源下载模块：
在第三方AsyncHttpClient方案增加BreakpointHttpResponseHandler类，支持多并发、多文件上传、断点续传、暂停、继续、删除下载任务。
 
使用如下：

 downloadMgr = DownloadManager.getInstance();
     downloadMgr.setDownLoadCallback(new DownLoadCallback() {
 
        @Override
        public void onLoading(String url, int bytesWritten, int totalSize) {
            super.onLoading(url, bytesWritten, totalSize);
        }
 
        @Override
        public void onSuccess(String url, String filePath) {
            super.onSuccess(url, filePath);
        }
 
        @Override
        public void onFailure(String url, String strMsg) {
            super.onFailure(url, strMsg);
        }
    });
 
    //添加下载任务
    for (DownloadInfo bean1 : list) {
        downloadMgr.addHandler(bean1.getUrl());
    }
 
图片下载模块：
采用universal-image-loader解决方案，有关使用情况请参见universal-image-loader官网信息，这里贴出github地址：https://github.com/nostra13/Android-Universal-Image-Loader
 
看到这里，估计您和您的小伙伴们都惊呆了吧！赶快动手试试吧！
 
如果你也有改变世界，哪怕一点点的梦想的话，
如果你也有为Android开发者做出自己的一点点贡献的话，
欢迎随时加入我们。
 
如果任何问题或者建议，欢迎沟通。
295893265@qq.com
