package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mytuoke.bean.getZfbBean;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddzfbnumActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.zfb_name)
    EditText zfbName;
    @BindView(R.id.zfb_num)
    EditText zfbNum;
    @BindView(R.id.zfb_phone)
    EditText zfbPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.btn_addzfbfinish)
    Button btnAddzfbfinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addzfbnum;
    }

    @Override
    protected void initView() {
        tvTitle.setText("修改支付宝");
    }

    @Override
    protected void initData() {
        zfbmassage();
    }


    @OnClick()
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.btn_getCode, R.id.btn_addzfbfinish,R.id.v_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getCode:

                break;
            case R.id.btn_addzfbfinish:
                if (zfbName.getText().toString().length()<2){
                    toast("请输入支付宝名称");
                    return;
                }
                if (zfbNum.getText().toString().length()<10){
                    toast("请输入支付宝账号");
                    return;
                }
                if (zfbPhone.getText().toString().length()<10){
                    toast("请输入支付宝绑定的手机号");
                    return;
                }

                finish();
                break;
            case R.id.v_close:
                fileList();
                break;
        }
    }
    //获取用户支付宝信息
    private void zfbmassage() {
        addSubscribe(ApiManage.getInstance().getMainApi().getgetZfb(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<getZfbBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<getZfbBean> getZfbBeanBaseData) {
                        getZfbBean data= getZfbBeanBaseData.getData();
                        if (data!=null&&"".equals(data)){
                           zfbName.setText(data.getZfb_name());
                           zfbNum.setText(data.getZfb_account());
                           zfbPhone.setText(data.getUser_phone());
                        }else{
                            zfbName.setText("");
                            zfbNum.setText("");
                            zfbPhone.setText("");
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }

    //绑定用户支付宝信息
    private void addzfbmassage() {
     /*   HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("bankName", );
        paramsMap.put("bankUser", );
        paramsMap.put("bankNo", );
        paramsMap.put("bankNo", );*/
        addSubscribe(ApiManage.getInstance().getMainApi().getgetZfb(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<getZfbBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<getZfbBean> getZfbBeanBaseData) {
                        getZfbBean data= getZfbBeanBaseData.getData();
                        if (data!=null&&"".equals(data)){
                            zfbName.setText(data.getZfb_name());
                            zfbNum.setText(data.getZfb_account());
                            zfbPhone.setText(data.getUser_phone());
                        }else{
                            zfbName.setText("");
                            zfbNum.setText("");
                            zfbPhone.setText("");
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }
}
