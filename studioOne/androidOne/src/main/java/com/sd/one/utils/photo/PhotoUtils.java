package com.sd.one.utils.photo;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

/**
 * [从本地选择图片以及拍照工具类，完美适配2.0-5.0版本]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-1-7
 * 
 **/
public class PhotoUtils {
	
	private final String tag = PhotoUtils.class.getSimpleName();
	
	/** 4.4以下(也就是kitkat以下)的版本 **/
	public static final int KITKAT_LESS = 0;
	/** 4.4以上(也就是kitkat以上)的版本,当然也包括最新出的5.0棒棒糖 **/
	public static final int KITKAT_ABOVE = 1;
	
	/** 裁剪图片成功后返回 **/ 
	public static final int INTENT_CROP = 2;
	/** 拍照成功后返回 **/ 
	public static final int INTENT_TAKE = 3;
	
	/** PhotoUtils对象 **/ 
	private Activity mActivity;
	private PhotoParams photoParams;
	private OnPhotoResultListener onPhotoResultListener;

	/**
	 * 构造方法
	 */
	public PhotoUtils(Activity activity) {
		mActivity = activity;
	}
	
	/**
	 * 拍照
	 * @param uri
	 * @return
	 */
	public void takePicture(Activity activity, PhotoParams param) {
		photoParams = param;
		if(param == null || param.outputUri == null){
			Log.e(tag, "takePicture param.outputUri is not null");
			return;
		}
		
		//每次选择图片吧之前的图片删除
		clearCropFile(photoParams.outputUri);
				
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, photoParams.outputUri);
		activity.startActivityForResult(intent, INTENT_TAKE);
    }
	 
	/***
	 * 选择一张图片
	 * 图片类型，这里是image/*，当然也可以设置限制
	 * 如：image/jpeg等
	 * @param activity Activity
	 */
	@SuppressLint("InlinedApi")
	public void selectPicture(Activity activity, PhotoParams param) {
		photoParams = param;
		if(param == null || param.outputUri == null){
			Log.e(tag, "takePicture param.outputUri is not null");
			return;
		}
		
		//每次选择图片吧之前的图片删除
		clearCropFile(photoParams.outputUri);
		
		if (Build.VERSION.SDK_INT < 19) {
			Intent intent = new Intent();
			intent.setType(photoParams.type);
			intent.setAction(Intent.ACTION_GET_CONTENT);
			activity.startActivityForResult(intent, KITKAT_LESS);
		} else {
			Intent intent = new Intent();
			intent.setType(photoParams.type);
			//由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
			//所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
			//如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
			//也就不会出现任何因为这句代码引发的错误
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			activity.startActivityForResult(intent, KITKAT_ABOVE);
		}
	}
	
	
	/***
	 * 裁剪图片
	 * @param activity
	 * @param param
	 */
	public void cropPicture(Activity activity) {
		if(photoParams == null){
			Log.e(tag, "cropPicture param is not null");
		}
		Intent innerIntent = new Intent("com.android.camera.action.CROP");
		innerIntent.setDataAndType(photoParams.uri, photoParams.type);
		
		// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
		innerIntent.putExtra("crop", photoParams.crop); 
		
		if(photoParams.aspectX > 0){
			// 放大缩小比例的X
			innerIntent.putExtra("aspectX", photoParams.aspectX); 
		}
		if(photoParams.aspectY > 0){
			// 放大缩小比例的X   这里的比例为：   1:1
			innerIntent.putExtra("aspectY", photoParams.aspectY);
		}
		if(photoParams.outputX > 0){
			//这个是限制输出图片大小
			innerIntent.putExtra("outputX", photoParams.outputX);  
		}
		if(photoParams.outputY > 0){
			//这个是限制输出图片大小
			innerIntent.putExtra("outputY", photoParams.outputY); 
		}
		
		innerIntent.putExtra("return-data", photoParams.returnData);
		innerIntent.putExtra("scale", photoParams.scale);
		innerIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoParams.outputUri);
		activity.startActivityForResult(innerIntent, INTENT_CROP);
	}
	
	
	/**
	 * 不裁剪，且不限制图片大小
	 * @param activity
	 * @param param
	 */
	public void cropFalsePicture(Activity activity) {
		photoParams.crop = String.valueOf(false);
		photoParams.aspectX = -1;
		photoParams.aspectY = -1;
		photoParams.outputX = -1;
		photoParams.outputY = -1;
		cropPicture(activity);
	}
	
	/**
	 * 返回结果处理
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(onPhotoResultListener == null){
			Log.e(tag, "onPhotoResultListener is not null");
			return;
		}
		
		switch (requestCode) {
			//拍照
			case INTENT_TAKE:
				if(resultCode == Activity.RESULT_OK){
					cropPicture(mActivity);
				}else{
					onPhotoResultListener.onPhotoCancel();
				}
				break;
			
			//4.4以下(也就是kitkat以下)的版本
			case KITKAT_LESS:
				if(data != null){
					photoParams.uri = data.getData();
					cropPicture(mActivity);
				}else{
					onPhotoResultListener.onPhotoCancel();
				}
				break;
				
			//4.4以上(也就是kitkat以上)的版本,当然也包括最新出的5.0棒棒糖
			case KITKAT_ABOVE:
				if(data != null){
					Uri uri = data.getData();
					String thePath = getPath(mActivity, uri);
					photoParams.uri = Uri.fromFile(new File(thePath));
					cropPicture(mActivity);
				}else{
					onPhotoResultListener.onPhotoCancel();
				}
				break;
				
			//截图
			case INTENT_CROP:
				if(resultCode == Activity.RESULT_OK){
					onPhotoResultListener.onPhotoResult(photoParams.outputUri);
				}else{
					onPhotoResultListener.onPhotoCancel();
				}
				break;
		}
	}
	
	/**
	 * 下面几个方法来自于stackoverflow,虽然来自大神，但大神的代码也不是就那样？
	 * 看不懂的地方挨个百度。
	 * -----------------------割-------------------------
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @author paulburke
	 */
	@SuppressLint("NewApi")
	public String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(uri)){
				return uri.getLastPathSegment();
			}
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
	
	
	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	/**
	 * 删除文件
	 * @param uri
	 * @return
	 */
	public boolean clearCropFile(Uri uri) {
        if (uri == null){
        	return false;
        }

        File file = new File(uri.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result){
            	Log.i(tag, "Cached crop file cleared.");
            }else{
            	Log.e(tag, "Failed to clear cached crop file.");
            }
            return result;
        } else {
            Log.w(tag, "Trying to clear cached crop file but it does not exist.");
        }
        
        return false;
    }
	 
	/**
	 * [回调监听类]
	 * 
	 * @author huxinwu
	 * @version 1.0
	 * @date 2015-1-7
	 * 
	 **/
	public interface OnPhotoResultListener{
		public void onPhotoResult(Uri uri);
		public void onPhotoCancel();
	}

	public OnPhotoResultListener getOnPhotoResultListener() {
		return onPhotoResultListener;
	}

	public void setOnPhotoResultListener(OnPhotoResultListener onPhotoResultListener) {
		this.onPhotoResultListener = onPhotoResultListener;
	}
	
}
