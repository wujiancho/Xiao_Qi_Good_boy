package com.lx.xqgg.face_ui.home;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.vip.bean.PayListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GaoEnrollActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_cpmpany_name)
    EditText etCpmpanyName;
    @BindView(R.id.et_zw)
    EditText etZw;
    @BindView(R.id.btn_enroll)
    Button btnEnroll;

    private List<PayListBean> list = new ArrayList<>();
    private PayFragment payFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gao_enroll_first;
    }

    @Override
    protected void initView() {
        tvTitle.setText("高参汇报名");
    }

    @Override
    protected void initData() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "faceProductProce");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getPayList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<PayListBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<PayListBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                list.clear();
                                list.addAll(listBaseData.getData());

                                return;
                            }
                        }
                        toast("获取配置失败！");
                        finish();
                    }
                }));
    }


    @OnClick({R.id.v_close, R.id.btn_enroll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_enroll:
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    toast("请输入姓名");
                    return;
                }
                if (etPhone.getText().toString().trim().length() < 11) {
                    toast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
                    toast("请输入公司名称");
                    return;
                }
                payFragment = new PayFragment(list, etName.getText().toString().trim(), etPhone.getText().toString().trim(), etCpmpanyName.getText().toString().trim(), etZw.getText().toString().trim());
                payFragment.show(getSupportFragmentManager(), "");
                break;
        }
    }
}
