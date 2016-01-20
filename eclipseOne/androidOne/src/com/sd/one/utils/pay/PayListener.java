package com.sd.one.utils.pay;

public interface PayListener {
	
	/**
	 * 支付模块监听
	 * 
	 * @param state 支付模块状态
	 * PayUtils.PAY_INIT_CODE =  10400   支付初始化错误
	 * PayUtils.PAY_GOODS_CODE = 18400   支付商品不完整错误
	 * PayUtils.PAY_SUC_CODE =   9000     支付成功
	 * PayUtils.PAY_DOING_CODE = 8000     支付结果确认中
	 * PayUtils.PAY_FAIL_CODE =  7000     支付失败
	 * 
	 * @param message
	 */
	public void onPayResult(int state, String message);
}
