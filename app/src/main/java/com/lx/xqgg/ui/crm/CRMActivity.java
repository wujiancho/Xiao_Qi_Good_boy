package com.lx.xqgg.ui.crm;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.helper.SharedPrefManager;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CRMActivity extends BaseActivity {

    @BindView(R.id.crmwv)
    WebView crmwv;
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.v_finish)
    View vFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;

    protected int getLayoutId() {
        return R.layout.activity_crm;
    }

    @Override
    protected void initView() {
        String username = getIntent().getStringExtra("username");
        tvTitle.setText(username + "服务商");
        HashMap<String, Object> paramsMap = new HashMap<>();
        if (SharedPrefManager.getUser() == null) {
            toast("或者用户信息失败");
            return;
        }
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
       /*addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ));*/
    }

    @Override
    protected void initData() {

    }


}
