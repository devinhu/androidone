package com.sd.one.utils;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import java.io.File;

/**
 * [版本更新服务类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 *
 **/
public class VersionUpdateService extends Service {

    private static final String TAG = VersionUpdateService.class.getSimpleName();

    private DownloadManager mDownloadManager;
    private BroadcastReceiver mReceiver;
    private String mDesc;
    private String mFileName;
    private String mUrl;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDesc = intent.getStringExtra("desc");
        mFileName = intent.getStringExtra("fileName");
        mUrl = intent.getStringExtra("url");
        NLog.e(TAG, "url: " + mUrl);

        if (TextUtils.isEmpty(mUrl)) {
            stopSelf();
        }

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + mFileName)), "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        };

        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void startDownload() {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mUrl));
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("正在下载");
        request.setDescription(mDesc);

        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName);
        mDownloadManager.enqueue(request);
    }

    /**
     * 比较版本，是否有新版本
     **/
    public static boolean hasNewVersion(String webVersion, String localVersion){
        if(!TextUtils.isEmpty(webVersion)){
            webVersion = (webVersion.replace(".", ""));
        }
        if(!TextUtils.isEmpty(localVersion)){
            localVersion = (localVersion.replace(".", ""));
        }

        try {
            if(!TextUtils.isEmpty(webVersion) && !TextUtils.isEmpty(localVersion)){
                if(Integer.parseInt(webVersion) > Integer.parseInt(localVersion)){
                    return true;
                }
            }
        }catch (Exception e){

        }
        return false;
    }
}