package com.lx.xqgg.ui.counter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.helper.SharedPrefManager;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CounterActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_ljhk)
    TextView tvLjhk;
    @BindView(R.id.tv_zflx)
    TextView tvZflx;
    @BindView(R.id.et_dkje)
    EditText etDkje;
    @BindView(R.id.et_dknx)
    EditText etDknx;
    @BindView(R.id.et_dkll)
    EditText etDkll;
    @BindView(R.id.rb_debx)
    RadioButton rbDebx;
    @BindView(R.id.rb_debj)
    RadioButton rbDebj;
    @BindView(R.id.btn_count)
    Button btnCount;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.daijin)
    TextView daijin;
    @BindView(R.id.dainian)
    TextView dainian;
    @BindView(R.id.daili)
    TextView daili;
    @BindView(R.id.ljhk)
    TextView ljhk;
    @BindView(R.id.huankfs)
    TextView huankfs;

    private int type = 0;
    private String loa;
    private String rat;
    private String pay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_counter_main;
    }

    @Override
    protected void initView() {
        tvTitle.setText("计算器");
        loa = SharedPrefManager.getImitationexamination().getPro_loa();
        rat = SharedPrefManager.getImitationexamination().getPro_rat();
        pay = SharedPrefManager.getImitationexamination().getPro_pay();
        daijin.setText(loa + "金额（万）");
        dainian.setText(loa + "年限（年）");
        daili.setText(loa + rat + "（%）");
        etDkje.setHint("请输入" + loa + "金额");
        etDkll.setHint("请输入" + loa + rat);
        etDknx.setHint("请输入" + loa + "年限");
        ljhk.setText("累计" + pay + "(元)");
        huankfs.setText(pay+"方式");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_debj) {
                    type = 1;
                } else {
                    type = 2;
                }
            }
        });

        etDkje.addTextChangedListener(new TextWatcher() {
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

    @OnClick({R.id.v_close, R.id.btn_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_count:
                if (TextUtils.isEmpty(etDkje.getText().toString())) {
                    toast("请输入" + loa + "金额");
                    return;
                }
                if (TextUtils.isEmpty(etDknx.getText().toString())) {
                    toast("请输入" + loa + "年限");
                    return;
                }
                if (TextUtils.isEmpty(etDkll.getText().toString())) {
                    toast("请输入" + loa + rat);
                    return;
                }
                if (Double.parseDouble(etDkll.getText().toString()) > 100.00) {
                    toast("请输入正确的" + loa + rat);
                    return;
                }
                if (type == 0) {
                    toast("请选择" + pay + "方式");
                    return;
                }
                if (etDkje.getText().toString().trim().endsWith(".")) {
                    toast("请输入正确的" + loa + "金额");
                    return;
                }

                double money = (Double.valueOf(etDkje.getText().toString()) * 10000);
                int month = Integer.parseInt(etDknx.getText().toString()) * 12;
                double rate = Double.parseDouble(etDkll.getText().toString());

                switch (type) {
                    case 1:
                        double hkNum = (month + 1) * money * (rate / 2400) + money;
                        double hkLx = (month + 1) * money * (rate / 2400);
                        tvLjhk.setText(String.format("%.2f", hkNum));
                        tvZflx.setText(String.format("%.2f", hkLx));
                        break;
                    case 2:
                        double bj = Double.parseDouble(money + "");
                        double yg = bj * (rate / 1200) * Math.pow((1 + (rate / 1200)), month) / (Math.pow(1 + (rate / 1200), month) - 1);
                        double hkNum1 = yg * month;

                        tvLjhk.setText(String.format("%.2f", hkNum1));
                        tvZflx.setText(String.format("%.2f", (hkNum1 - money)));
                        break;
                }
                Intent intent = new Intent(mContext, CounterDetailActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("year", Integer.parseInt(etDknx.getText().toString()));
                intent.putExtra("rate", rate);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
        }
    }


}
