/*
    ShengDao Android Client, BluetoothService
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.sd.core.utils.NLog;

/**
 * [蓝牙服务类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-7-21
 * 
 **/
public class BluetoothService {
	
    private static final String TAG = BluetoothService.class.getSimpleName();

    private static final String NAME = "BluetoothChat";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private int mState;
    private final BluetoothAdapter mAdapter;
    private BluetoothCallBack mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    public static final int STATE_UNAVAILABLE = -1;       
    public static final int STATE_NONE = 0;       
    public static final int STATE_LISTEN = 1;     
    public static final int STATE_CONNECTING = 2; 
    public static final int STATE_CONNECTED = 3;  

    
    /**
     * 构造方法
     * @param context
     * @param handler
     */
    public BluetoothService(BluetoothCallBack handler) {
    	mHandler = handler;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        setState(STATE_NONE);
        
        if(mAdapter == null){
        	setState(STATE_UNAVAILABLE, "bluetooth is unavailable.");
		}else{
			if(!mAdapter.isEnabled()){
				mAdapter.enable();
			}
		}
    }

    /**
     * 设置回调
     * @param handler
     */
    public void setBluetoothCallBack(BluetoothCallBack handler){
    	if(handler != null){
    		mHandler = handler;
    	}
    }
    
    /**
     * 开启方法
     */
    public synchronized void start() {
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    /**
     * 连接蓝牙
     * @param device
     */
    public synchronized void connect(BluetoothDevice device) {
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * 已经连接上蓝牙方法
     * @param socket
     * @param device
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        NLog.d(TAG, "connected");

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}

        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        setState(STATE_CONNECTED, device.getName());
    }

    /**
     * 断开蓝牙
     */
    public synchronized void stop() {
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        setState(STATE_NONE);
    }

    /**
     * 发送命令
     * @param requestCode 请求码
     * @param command 发送命令
     */
    public void request(int requestCode, byte[] command) {
    	request(requestCode, command, null);
    }
    
    /**
     * 发送命令
     * @param requestCode 请求码
     * @param command 发送命令
     * @param prefix 命令前缀（有时候是命令，有时候跟命令有区别，具体需要参考返回结果）
     */
    public void request(int requestCode, byte[] command, String prefix) {
        ConnectedThread r;
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        r.write(requestCode, command, prefix);
    }
    
    /**
     * 设置连接状态
     * @param state
     */
    private synchronized void setState(int state) {
    	setState(state, "");
    }
    
    /**
     * 设置连接状态
     * @param state
     */
    private synchronized void setState(int state, String message) {
        mState = state;
        mHandler.sendStateMessage(state, message);
    }

    /**
     * 得到连接状态
     * @return
     */
    public synchronized int getState() {
        return mState;
    }

    
    /**
     * [监听连接蓝牙线程]
     * 
     * @author devin.hu
     * @version 1.0
     * @date 2014-7-21
     * 
     **/
    private class AcceptThread extends Thread {
    	
        private final BluetoothServerSocket mmServerSocket;
        
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                NLog.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            setName("AcceptThread");
            BluetoothSocket socket = null;

            while (mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    NLog.e(TAG, "accept() failed", e);
                    break;
                }

                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            try {
                                socket.close();
                            } catch (IOException e) {
                                NLog.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                NLog.e(TAG, "close() of server failed", e);
            }
        }
    }


    /**
     * [连接蓝牙线程]
     * 
     * @author devin.hu
     * @version 1.0
     * @date 2014-7-21
     * 
     **/
    private class ConnectThread extends Thread {
    	
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                NLog.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");
            mAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
            } catch (IOException e) {
            	setState(STATE_LISTEN, "Unable to connect device");
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    NLog.e(TAG, "unable to close() socket during connection failure", e2);
                }
                BluetoothService.this.start();
                return;
            }

            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                NLog.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * [数据交换蓝牙线程]
     * 
     * @author devin.hu
     * @version 1.0
     * @date 2014-7-21
     * 
     **/
    private class ConnectedThread extends Thread {
    	
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        
        private String result;
        private int curRequestCode;
        private StringBuilder resultBuilder;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                NLog.e(TAG, "ConnectedThread temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
        	int bytes;
            byte[] buffer = new byte[1024];
            resultBuilder = new StringBuilder();
            
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    resultBuilder.append(new String(buffer, 0, bytes));
                    result = resultBuilder.toString();
                    NLog.e(TAG, "result: " + result);
                    
                    mHandler.sendResultMessage(curRequestCode, result);
            		resultBuilder = new StringBuilder();
            		
                } catch (IOException e) {
                    NLog.e(TAG, "ConnectedThread disconnected", e);
                    setState(STATE_LISTEN, "Device connection was lost");
                    break;
                }
            }
        }

        public void write(int requestCode, byte[] command, String prefix) {
            try {
                curRequestCode = requestCode;
                mmOutStream.write(command);
                mHandler.sendCommandMessage(requestCode);
            } catch (IOException e) {
            	curRequestCode = -1;
                NLog.e(TAG, "ConnectedThread write failed.", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                NLog.e(TAG, "ConnectedThread cancel socket failed.", e);
            }
        }
    }
}
