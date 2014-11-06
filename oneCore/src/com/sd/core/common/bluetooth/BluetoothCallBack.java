/*
    ShengDao Android Client, onDiagnoseListener
    Copyright (c) 2014 ShengDao Tech Company Limited
*/

package com.sd.core.common.bluetooth;

import android.os.Handler;
import android.os.Message;


/**
 * [蓝牙数据处理回调类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-6-28
 * 
 **/
public class BluetoothCallBack extends Handler{

	public static final int MESSAGE_STATE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
	    
	/**
	 * 蓝牙状态
	 * @param bluetoothState
	 *  BluetoothService.STATE_UNAVAILABLE = -1;  不可用    
	 *  BluetoothService.STATE_NONE = 0;          未连接
	 *  BluetoothService.STATE_LISTEN = 1;        等待连接
	 *  BluetoothService.STATE_CONNECTING = 2;    正在连接
	 *  BluetoothService.STATE_CONNECTED = 3;     已经连接
	 * @param message
	 */
	public void onStateChange(int bluetoothState, String message){}
	
	/**
	 * 发送命令
	 * @param requestCode
	 * @param command
	 */
	public void onCommand(int requestCode){}
	
	/**
	 * 接收蓝牙返回结果
	 * @param requsetCode 请求码
	 * @param command 发送的命令
	 * @param data 返回数据
	 */
	public void onResult(int requestCode, String data){}
	

	@Override
	public void handleMessage(Message msg) {
		Object[] response = null;
		switch (msg.what) {
			case MESSAGE_STATE:
				response = (Object[]) msg.obj;
				onStateChange((Integer)(response[0]), String.valueOf(response[1]));
				break;
				
			case MESSAGE_WRITE:
				response = (Object[]) msg.obj;
				onCommand((Integer)(response[0]));
				break;
				
			case MESSAGE_READ:
				response = (Object[]) msg.obj;
				onResult((Integer)(response[0]), String.valueOf(response[1]));
				break;
		}
	}
	
	/**
	 * 发送蓝牙状态改变消息
	 * @param state
	 * @param message
	 */
	protected void sendStateMessage(int state, String message) {
		sendMessage(obtainMessage(MESSAGE_STATE, new Object[] { state, message}));
	}

	/**
	 * 发送蓝牙命令消息
	 * @param requestCode
	 * @param command
	 */
	protected void sendCommandMessage(int requestCode) {
		sendMessage(obtainMessage(MESSAGE_WRITE, new Object[] { requestCode}));
	}

	/**
	 * 发送蓝牙结果消息
	 * @param requestCode
	 * @param command
	 * @param result
	 */
	protected void sendResultMessage(int requestCode, String result) {
		sendMessage(obtainMessage(MESSAGE_READ, new Object[] { requestCode, result }));
	}
}
