package com.lx.xqgg.ui.person;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.bean.UserServiceBean;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ServiceInfoActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_cpmpany_name)
    TextView tvCpmpanyName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("服务商信息");
        HashMap<String, Object> paramsMap = new HashMap<>();
        if(SharedPrefManager.getUser()==null){
            toast("获取用户信息失败！");
            return;
        }
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<UserServiceBean> objectBaseData) {
                        Log.e("zlz", new Gson().toJson(objectBaseData));
                        if (objectBaseData.isSuccess()) {
                            if (objectBaseData.getCode() == 1) {
                                tvCpmpanyName.setText(objectBaseData.getData().getService_name() + "");
                                tvAddress.setText(objectBaseData.getData().getAddress() + "");
                                tvPhone.setText(objectBaseData.getData().getPhone() + "");
                                tvName.setText(objectBaseData.getData().getLegal_person() + "");

                                String status = "";
                                switch (objectBaseData.getData().getStatus()) {
                                    case "normal":
                                        status = "已通过";
                                        break;
                                    case "rejected":
                                        status = "已拒绝";
                                        break;
                                    case "hidden":
                                        status = "审核中";
                                        break;
                                }
                                tvStatus.setText(status);
                            } else {
                                toast(objectBaseData.getMessage());
                            }
                        } else {
                            toast(objectBaseData.getMessage());
                        }
                    }
                }));


    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
