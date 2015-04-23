package com.sd.one.utils.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

/**
 * [支付页面]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 * 
 **/
public class PayUtils1  {

	/** 支付初始化错误 **/
	public static final int PAY_INIT_CODE = 10400;
	/** 支付商品不完整错误 **/
	public static final int PAY_GOODS_CODE = 18400;
	/** 支付成功 **/
	public static final int PAY_SUC_CODE = 9000;
	/** 支付结果确认中 **/
	public static final int PAY_DOING_CODE = 8000;
	/** 支付失败 **/
	public static final int PAY_FAIL_CODE = 7000;
	
	/** Activity **/
	private Activity mContext;
	/** 支付状态 **/
	private String resultStatus;
	/** 回调监听 **/
	private PayListener payListener;
	
	/**
	 * 构造方法
	 * @param mContext
	 */
	public PayUtils1(Activity mContext, PayListener listener) {
		this.mContext = mContext;
		this.payListener = listener;
	}

	/**
	 * 调用SDK支付
	 * @param goodsName 商品名称
	 * @param goodsDesc 商品描述
	 * @param goodsPrice 商品价格
	 * @param orderNo 订单号
	 * @throws Exception 
	 */
	public void pay(final String alipayParams) throws Exception {
		if(payListener != null){
			if(TextUtils.isEmpty(alipayParams)){
				payListener.onPayResult(PAY_INIT_CODE, "提交信息异常");
				return;
			}
		}
		Thread payThread = new Thread(new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(mContext);
				String result = alipay.pay(alipayParams);
				Message msg = new Message();
				msg.what = 10000;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		});
		
		payThread.start();
	}
	
	/**
	 * 回调结果处理
	 */
	@SuppressLint("HandlerLeak") 
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 10000: {
					if(msg.obj != null){
						String rawResult = String.valueOf(msg.obj);
						String[] resultParams = rawResult.split(";");
						for (String resultParam : resultParams) {
							if (resultParam.startsWith("resultStatus")) {
								resultStatus = gatValue(resultParam, "resultStatus");
							}
						}
					}
					
					if(payListener != null){
						if("9000".equals(resultStatus)){
							payListener.onPayResult(PAY_SUC_CODE, "支付成功");
						}else if("8000".equals(resultStatus)){
							payListener.onPayResult(PAY_DOING_CODE, "支付结果确认中");
						}else{
							payListener.onPayResult(PAY_FAIL_CODE, "支付失败");
						}
					}
					break;
				}
			}
		};
	};

	/**
	 * 根据key获取值
	 * @param content
	 * @param key
	 * @return
	 */
	private String gatValue(String content, String key) {
		String prefix = key + "={";
		return content.substring(content.indexOf(prefix) + prefix.length(), content.lastIndexOf("}"));
	}

	public PayListener getPayListener() {
		return payListener;
	}

	public void setPayListener(PayListener payListener) {
		this.payListener = payListener;
	}
}
