package com.lx.xqgg.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lx.xqgg.base.Constans;
import com.lx.xqgg.ui.vip.bean.NotifyBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constans.WECHAT_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                EventBus.getDefault().post(new NotifyBean(0, "succ"));
                Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                finish();
            } else {
                EventBus.getDefault().post(new NotifyBean(baseResp.errCode, "fail"));
                Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
