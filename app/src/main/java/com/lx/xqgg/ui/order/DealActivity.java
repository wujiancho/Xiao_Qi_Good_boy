package com.lx.xqgg.ui.order;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.bean.DealBean;
import com.lx.xqgg.ui.order.bean.OrderBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DealActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_kh_name)
    TextView tvKhName;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.et_ysx)
    EditText etYsx;
    @BindView(R.id.layout_ysx)
    LinearLayout layoutYsx;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.et_zstg)
    EditText etZstg;
    @BindView(R.id.layout_zstg)
    LinearLayout layoutZstg;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;
    @BindView(R.id.checkBox4)
    CheckBox checkBox4;
    @BindView(R.id.et_jj_reason)
    EditText etJjReason;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;


    private DealBean dealBean;
    private OrderBean.RecordsBean orderBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deal;
    }

    @Override
    protected void initView() {
        tvTitle.setText("立即处理");
        dealBean = new DealBean();
        orderBean = (OrderBean.RecordsBean) getIntent().getSerializableExtra("data");


        etYsx.addTextChangedListener(new TextWatcher() {
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

        tvOrderNum.setText("订单信息：" + orderBean.getOrderNo());
        tvCreateTime.setText("创建时间：" + orderBean.getCreatetime());
        tvCompany.setText("企业名称：" + orderBean.getApply_company());
        tvProductName.setText("产品名称：" + orderBean.getTitle());
        tvKhName.setText("客户名称：" + orderBean.getLink_man());
        Log.e("zlz", orderBean.getId() + "");
//        dealBean.set
        dealBean = new DealBean();
        dealBean.setId(orderBean.getId() + "");
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    layoutYsx.setVisibility(View.VISIBLE);
                    layoutZstg.setVisibility(View.GONE);
                    etJjReason.setVisibility(View.GONE);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    layoutYsx.setVisibility(View.GONE);
                    layoutZstg.setVisibility(View.VISIBLE);
                    etJjReason.setVisibility(View.GONE);
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    layoutYsx.setVisibility(View.GONE);
                    layoutZstg.setVisibility(View.GONE);
                    etJjReason.setVisibility(View.VISIBLE);
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    layoutYsx.setVisibility(View.GONE);
                    layoutZstg.setVisibility(View.GONE);
                    etJjReason.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.v_close, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        if (!(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked())) {
            toast("请选择");
            return;
        }
        //废单特殊处理
        if (checkBox4.isChecked()) {
            MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                    .title("提示")
                    .content("系统即将删除该笔订单，请确认")
                    .cancelable(false)
                    .positiveText(R.string.yes)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            HashMap<String, Object> paramsMap = new HashMap<>();
                            paramsMap.put("id", orderBean.getId());
                            paramsMap.put("token", SharedPrefManager.getUser().getToken());
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                            addSubscribe(ApiManage.getInstance().getMainApi().disCardOrder(body)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                        @Override
                                        public void onNext(BaseData baseData) {
                                            Log.e("zlz",new Gson().toJson(baseData));
                                            if(baseData.isSuccess()){
                                                toast("提交成功");
                                                EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                finish();
                                            }else {
                                                toast(baseData.getMessage());
                                            }
                                        }
                                    }));
                        }
                    })
                    .negativeText(R.string.no)
                    .negativeColorRes(R.color.txt_normal)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .build();
            permissionDialog.show();
        } else {
            if (checkBox1.isChecked()) {
                if (TextUtils.isEmpty(etYsx.getText().toString())) {
                    toast("请输入预授信金额");
                    return;
                }
                if ((Double.parseDouble(etYsx.getText().toString()) * 10000) > orderBean.getApply_money()) {
                    toast("预授信金额不能大于申请金额！");
                    return;
                }
                dealBean.setPre_credit_money((Double.parseDouble(etYsx.getText().toString()) * 10000) + "");
                dealBean.setStatus("precredit");
            }
            if (checkBox2.isChecked()) {
                if (TextUtils.isEmpty(etZstg.getText().toString())) {
                    toast("请输入授信金额");
                    return;
                }
                if ((Double.parseDouble(etZstg.getText().toString()) * 10000) > orderBean.getApply_money()) {
                    toast("授信金额不能大于申请金额！");
                    return;
                }
                dealBean.setReal_money((Double.parseDouble(etZstg.getText().toString()) * 10000) + "");
                dealBean.setStatus("pass");
            }
            if (checkBox3.isChecked()) {
                if (TextUtils.isEmpty(etJjReason.getText().toString())) {
                    toast("请输入拒绝理由");
                    return;
                }
                dealBean.setRej_reason(etJjReason.getText().toString());
                dealBean.setStatus("nopass");
            }

            Gson gson = new Gson();
            String obj = gson.toJson(dealBean);
            Log.e("zlz", new Gson().toJson(dealBean));
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
        }
    }
}
