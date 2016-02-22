package com.sd.one.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sd.one.R;
import com.sd.one.common.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * [微信支付回调页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-8-4
 * 
 **/
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(0 == resp.errCode){
				Toast.makeText(this, "支付成功", 1000).show();
				WXPayEntryActivity.this.finish();
			}else{
				Toast.makeText(this, "支付失败", 1000).show();
				WXPayEntryActivity.this.finish();
			}
		}
	}
}