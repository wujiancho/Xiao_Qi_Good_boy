package com.lx.xqgg.ui.order;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.ui.order.bean.DealBean;
import com.lx.xqgg.ui.order.bean.OrderBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;

import org.greenrobot.eventbus.EventBus;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//用信额度 订单立即用信页面
public class CreditNowActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_zstg)
    EditText etZstg;
    @BindView(R.id.layout_zstg)
    LinearLayout layoutZstg;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private DealBean dealBean;
    private OrderBean.RecordsBean orderBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_now;
    }

    @Override
    protected void initView() {
        tvTitle.setText("立即用信");
        orderBean = (OrderBean.RecordsBean) getIntent().getSerializableExtra("data");
        dealBean = new DealBean();
        dealBean.setId(orderBean.getId() + "");

        etZstg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.btn_confirm, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(etZstg.getText().toString().trim())) {
                    toast("请输入用信金额");
                    return;
                }
                if (Double.parseDouble(etZstg.getText().toString()) * 10000 > Double.parseDouble(orderBean.getReal_money())) {
                    toast("用信金额不能大于终审金额！");
                    return;
                }
                dealBean.setStatus("usecredit");
                dealBean.setCredit_money((Double.parseDouble(etZstg.getText().toString()) * 10000) + "");
                Gson gson = new Gson();
                String obj = gson.toJson(dealBean);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj);
                addSubscribe(ApiManage.getInstance().getMainApi().updateOrder(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, null) {
                            @Override
                            public void onNext(BaseData<Object> objectBaseData) {
                                if (objectBaseData.isSuccess()) {
                                    toast("提交成功");
                                    EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                    finish();
                                } else {
                                    toast(objectBaseData.getMessage());
                                }
                            }
                        }));
                break;
        }
    }
}
