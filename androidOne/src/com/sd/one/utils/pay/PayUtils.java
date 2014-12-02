package com.sd.one.utils.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

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
 * 具体使用：
 * 
    String PARTNER = "2088XXXXXXXXXXXX";
	String SELLER = "demo@yahoo.cn";
	String RSA_PRIVATE = "私钥";
	PayUtils payutils = new PayUtils(activity, PARTNER, SELLER, RSA_PRIVATE, "服务器回调订单地址[异步处理]");
	payutils.setPayListener(new PayListener() {
		@Override
		public void onPayResult(int state, String message) {
			
		}
	});
	
	try {
		payutils.pay("测试商品", "测试商品详情", "0.01", "唯一订单号，服务器生成");
	} catch (Exception e) {
		e.printStackTrace();
	}
 * 
 **/
public class PayUtils  {

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
	
	/** 合作商家id **/
	private String PARTNER;
	/** 卖家账户 **/
	private String SELLER;
	/** 私钥 **/
	private String RSA_PRIVATE;
	/** 回调地址 **/
	private String notify_url;
	/** 支付状态 **/
	private String resultStatus;
	/** 回调监听 **/
	private PayListener payListener;
	
	/**
	 * 构造方法
	 * @param mContext
	 */
	public PayUtils(Activity mContext, String partner, String seller, String rsa_private) {
		this(mContext, partner, seller, rsa_private, "", null);
	}
	
	/**
	 * 构造方法
	 * @param mContext
	 */
	public PayUtils(Activity mContext, String partner, String seller, String rsa_private, String return_url) {
		this(mContext, partner, seller, rsa_private, return_url, null);
	}
	
	/**
	 * 构造方法
	 * @param mContext
	 */
	public PayUtils(Activity mContext, String partner, String seller, String rsa_private, String notify_url, PayListener listener) {
		this.mContext = mContext;
		this.PARTNER = partner;
		this.SELLER = seller;
		this.RSA_PRIVATE = rsa_private;
		this.notify_url = notify_url;
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
	public void pay(String goodsName, String goodsDesc, String goodsPrice, String orderNo) throws Exception {
		if(payListener != null){
			if(TextUtils.isEmpty(PARTNER)){
				payListener.onPayResult(PAY_INIT_CODE, "合作商家ID不能为空");
				return;
			}
			if(TextUtils.isEmpty(SELLER)){
				payListener.onPayResult(PAY_INIT_CODE, "卖家账户不能为空");
				return;
			}
			if(TextUtils.isEmpty(RSA_PRIVATE)){
				payListener.onPayResult(PAY_INIT_CODE, "私钥不能为空");
				return;
			}
			if(TextUtils.isEmpty(goodsName)){
				payListener.onPayResult(PAY_GOODS_CODE, "商品名称不能为空");
				return;
			}
			if(TextUtils.isEmpty(goodsPrice)){
				payListener.onPayResult(PAY_GOODS_CODE, "商品价格不能为空");
				return;
			}
			if(TextUtils.isEmpty(orderNo)){
				payListener.onPayResult(PAY_GOODS_CODE, "订单号不能为空");
				return;
			}
		}else{
			throw new Exception("PayListener 支付监听不能为空.");
		}
		
		String orderInfo = getOrderInfo(goodsName, goodsDesc, goodsPrice, orderNo);
		String sign = sign(orderInfo);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		
		Thread payThread = new Thread(new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(mContext);
				String result = alipay.pay(payInfo);
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
	 * 创建订单信息
	 * @param subject
	 * @param body
	 * @param price
	 * @return
	 */
	private String getOrderInfo(String subject, String body, String price, String orderNo) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * 对订单信息进行签名
	 * @param content 待签名订单信息
	 * @return
	 */
	private String sign(String content) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(RSA_PRIVATE));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes("UTF-8"));
			
			byte[] signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 获取签名方式
	 * @return
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

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
