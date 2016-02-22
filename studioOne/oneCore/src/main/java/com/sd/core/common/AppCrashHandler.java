/*
    ShengDao Android Client, AppCrashHandler
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import com.sd.core.utils.NLog;
 
/**
 * [系统未捕获异常默认处理类，将完成当系统出现异常（未处理）时，弹出友好提示框，收集错误日记，发送错误日志于服务器]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-8-20
 * 
 **/
public class AppCrashHandler implements UncaughtExceptionHandler {

	private final String tag = AppCrashHandler.class.getSimpleName();
	
	private Context mContext;
	private static AppCrashHandler instance;
	private UncaughtExceptionHandler mDefaultHandler;
	private APPOnCrashListener onCrashListener;
	private StringBuilder crashReport = new StringBuilder();
	
	private final String VERSIONNAME = "versionName"; 
	private final String VERSIONCODE = "versionCode"; 
	
	private final String PREFIX = "crash_";
	private final String PATTERN = "yyyy-MM-dd hh:mm:ss";
	private final String SUFFIX = ".txt";
	private String path;
	
	
	/**
	 * 单例模式实现获取AppCrashHandler实例
	 * @return
	 */
	public static AppCrashHandler getInstance(Context context) {
		if(instance == null){
			instance = new AppCrashHandler(context);
		}
		return instance;
	}

	/**
	 * 构造方法
	 * @param context
	 */
	private AppCrashHandler(Context context) {
		mContext = context;
		if(checkSDCard()){
			path = Environment.getExternalStorageDirectory().getPath() + File.separator + mContext.getPackageName();
		}else{
			path = mContext.getFilesDir().getParent();
		}
	    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handlerException(ex) && mDefaultHandler != null){
			//如果系统出现未捕获的异常，mDefaultHandler将处理它
			mDefaultHandler.uncaughtException(thread, ex);
		}else{
			try {
				Thread.sleep(5000);
				ActivityPageManager.getInstance().exit(mContext);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 出现未捕获的异常的处理方法
	 * @param ex
	 * @return
	 */
	private boolean handlerException(Throwable ex){
		if(ex == null ){
			return true;
		}
		
		final String msg = ex.getLocalizedMessage();
		if(TextUtils.isEmpty(msg)){
			return false;
		}
		
		new Thread(){

			@Override
			public void run() {
				Looper.prepare();
				//当出现异常是，友好处理
				if(onCrashListener != null){
					onCrashListener.onCrashDialog(mContext);
				}
				Looper.loop();
			}
			
		}.start();
		
		//收集错误信息
		conllectCrashDeviceInfo(mContext);
		
		//保持错误报告到本地
		saveCrashInfo(ex);
		
		//发送错误报告到服务器，并删除本地文件
		sendCrashReport();
		
		return true;
	}


	/**
	 * 当系统crash的时候，收集错误信息
	 * @param context
	 */
	private void conllectCrashDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if(pi != null){
				crashReport.append("\n\t");
				crashReport.append(VERSIONNAME +" = "+ String.valueOf(pi.versionName)).append("\n\t");
				crashReport.append(VERSIONCODE +" = "+ String.valueOf(pi.versionCode)).append("\n\t");
			}
			
			//根据反射获取Build的所有信息
			Field[] fieldList = Build.class.getDeclaredFields();
			if(fieldList != null){
				for(Field device : fieldList){
					device.setAccessible(true);
					crashReport.append(device.getName() +" = "+ String.valueOf(device.get(null))).append("\n\t");
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存错误报告到本地
	 * @param ex
	 */
	private void saveCrashInfo(Throwable ex) {
		try {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			
			Throwable cause = ex.getCause();
			while(cause != null){
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			
			String result = writer.toString();
			printWriter.close();
			
			crashReport.append("\n\t");
			crashReport.append(ex.getMessage()).append("\n\t");
			crashReport.append(result).append("\n\t");
			
			String fileName = getCrashFileName();
			File file = new File(path);  
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(path, fileName); 
			NLog.e(tag, file.getPath());
			
			FileOutputStream fos = new FileOutputStream(file);  
			fos.write(crashReport.toString().getBytes());
			fos.flush();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断SDCard是否存在,并可写
	 * @return
	 */
	public boolean checkSDCard(){
		String flag = Environment.getExternalStorageState();
		if(android.os.Environment.MEDIA_MOUNTED.equals(flag)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取错误报告文件名称
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public String getCrashFileName(){
		StringBuilder fileName = new StringBuilder(PREFIX);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
		fileName.append(sdf.format(date));
		
		fileName.append(SUFFIX);
		return fileName.toString();
	}
	
	/**
	 * 发送错误日志报告，并删除本地文件
	 */
	public void sendCrashReport(){
		File filesDir = mContext.getFilesDir();
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(SUFFIX);
			}
		};
		
		String[] list = filesDir.list(filter);
		if(list != null && list.length > 0){
			for(String fileName : list){
				File file = new File(path, fileName);
				if(file.exists()){
					if(onCrashListener != null){
						onCrashListener.onCrashPost(String.valueOf(crashReport), file);
					}
				}
			}
		}
	}

	public APPOnCrashListener getOnCrashListener() {
		return onCrashListener;
	}

	public void setOnCrashListener(APPOnCrashListener onCrashListener) {
		this.onCrashListener = onCrashListener;
	}
}
