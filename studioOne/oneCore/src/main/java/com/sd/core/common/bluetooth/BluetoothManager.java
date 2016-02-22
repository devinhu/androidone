/*
    ShengDao Android Client, BluetoothManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common.bluetooth;

import android.bluetooth.BluetoothDevice;
import com.sd.core.utils.NLog;

/**
 * [蓝牙管理类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-7-21
 * 
 **/
public class BluetoothManager {

	private final String tag = BluetoothManager.class.getSimpleName();
	private static BluetoothService mChatService;
	private static BluetoothManager instance;
	
	/**
	 * 得到单例模式BluetoothManager对象
	 * @return
	 */
	public static BluetoothManager getInstance(BluetoothCallBack callBack) {
		if (instance == null) {
			synchronized (BluetoothManager.class) {
				if (instance == null) {
					instance = new BluetoothManager(callBack);
				}
			}
		}
		
		if(callBack != null && mChatService != null){
			mChatService.setBluetoothCallBack(callBack);
		}
		
		return instance;
	}
	
	/**
	 * 构造方法
	 * @param callBack
	 */
	private BluetoothManager(BluetoothCallBack callBack) {
		mChatService = new BluetoothService(callBack);
	}

	/**
	 * 开启服务
	 */
	public void start() {
		if (mChatService != null) {
			if (mChatService.getState() == BluetoothService.STATE_CONNECTED) {
				mChatService.stop();
			}
			if (mChatService.getState() == BluetoothService.STATE_NONE) {
				mChatService.start();
			}
		}
	}

	/**
	 * 连接设备
	 * @param device
	 */
	public void connect(BluetoothDevice device) {
		if (mChatService != null) {
			mChatService.connect(device);
		}
	}

	/**
	 * 发送请求
	 * @param requestCode
	 * @param command
	 */
	public void request(int requestCode, byte[] command) {
		if(command == null){
			NLog.e(tag, "requestCode is error, so command is null");
			return;
		}
		if (mChatService != null) {
			if(mChatService.getState() == BluetoothService.STATE_CONNECTED){
				mChatService.request(requestCode, command);
			}else{
				NLog.e(tag, "bluetoothState: " + mChatService.getState());
			}
		}
	}
	
	/**
	 * 停止服务
	 */
	public void stop() {
		if (mChatService != null) {
			mChatService.stop();
		}
	}
}
