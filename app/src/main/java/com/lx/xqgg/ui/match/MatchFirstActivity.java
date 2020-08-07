package com.lx.xqgg.ui.match;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.bean.MatchRequestBean;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MatchFirstActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.checkBox4)
    CheckBox checkBox4;
    @BindView(R.id.layout_yq)
    LinearLayout layoutYq;
    @BindView(R.id.switch_yq)
    Switch switchYq;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_qy_name)
    EditText etQyName;
    @BindView(R.id.sw_po)
    Switch swPo;
    @BindView(R.id.sw_fd)
    Switch swFd;
    @BindView(R.id.sw_24)
    Switch sw24;
    @BindView(R.id.sw_qs)
    Switch swQs;
    @BindView(R.id.et_yqje)
    EditText etYqje;
    @BindView(R.id.et_yq_count)
    EditText etYqCount;
    @BindView(R.id.layout_yqje)
    LinearLayout layoutYqje;
    @BindView(R.id.checkBox63)
    CheckBox checkBox63;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.famg)
    TextView famg;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.btn_next)
    Button btnNext;

    private MatchRequestBean matchRequestBean;

    public static MatchFirstActivity instance;
    private String hou;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_first;
    }

    @Override
    protected void initView() {
        instance = this;
        tvTitle.setText("智能匹配");
        hou = SharedPrefManager.getImitationexamination().getPro_hou();
        famg.setText("是否有"+hou);
        matchRequestBean = new MatchRequestBean();
        //配偶知情默认否
        matchRequestBean.setSpouseInformed("0");
        //按揭默认否
        matchRequestBean.setMortgage("0");
        //缴满24月默认否
        matchRequestBean.setTwoYearPolicy("0");
        //企业当月欠税默认否
        matchRequestBean.setTaxOwed("0");
        //个人征信逾期默认否
        matchRequestBean.setOverdue("0");
        //近6个月逾期默认0
        matchRequestBean.setSixMonthOverdueTimes(0);
        //12yue逾期90天，默认否
        matchRequestBean.setYearOverdueTimesThreesTimes("0");
        //24月逾期120天，默认否
        matchRequestBean.setTwoYearOverdueFourTimes("0");
//        swPo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    matchRequestBean.setSpouseInformed("1");
//                } else {
//                    matchRequestBean.setSpouseInformed("0");
//                }
//            }
//        });
        swFd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    matchRequestBean.setMortgage("1");
                } else {
                    matchRequestBean.setMortgage("0");
                }
            }
        });
        sw24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    matchRequestBean.setTwoYearPolicy("1");
                } else {
                    matchRequestBean.setTwoYearPolicy("0");
                }
            }
        });
        swQs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    matchRequestBean.setTaxOwed("1");
                } else {
                    matchRequestBean.setTaxOwed("0");
                }
            }
        });
        switchYq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    layoutYqje.setVisibility(View.VISIBLE);
                    matchRequestBean.setOverdue("1");
                } else {
//                    layoutYqje.setVisibility(View.GONE);
                    matchRequestBean.setOverdue("0");
                }
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    matchRequestBean.setSixMonthOverdueTimes(0);
                    checkBox2.setChecked(false);
                    matchRequestBean.setYearOverdueTimesThreesTimes("0");
                    checkBox3.setChecked(false);
                    matchRequestBean.setTwoYearOverdueFourTimes("0");
                    checkBox63.setChecked(false);
                    checkBox1.setClickable(false);
                    checkBox2.setClickable(false);
                    checkBox3.setClickable(false);
                    checkBox63.setClickable(false);
                } else {
                    checkBox1.setClickable(true);
                    checkBox2.setClickable(true);
                    checkBox3.setClickable(true);
                    checkBox63.setClickable(true);
                }
            }
        });

    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.v_close, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }

    private void next() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            toast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(etAge.getText().toString().trim())) {
            toast("请输入年龄");
            return;
        }
        if (Integer.parseInt(etAge.getText().toString()) < 18 || Integer.parseInt(etAge.getText().toString()) > 75) {
            toast("申请人年龄必须在18~75岁之间");
            return;
        }
        if (TextUtils.isEmpty(etQyName.getText().toString().trim())) {
            toast("请填写企业名称");
            return;
        }
//        if (switchYq.isChecked()) {
//            if (TextUtils.isEmpty(etYqje.getText().toString())) {
////                toast("请输入逾期金额");
//                showDialog("请输入逾期金额");
//                return;
//            }
//        }
        if (TextUtils.isEmpty(etYqCount.getText().toString().trim())) {
            toast("请输入个人征信合计逾期次数");
            return;
        }
        if (!(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() || checkBox63.isChecked())) {
            toast("请勾选逾期情况");
            return;
        }
        if (checkBox1.isChecked()) {
            matchRequestBean.setSixMonthOverdueTimes(2);
        }
        if (checkBox63.isChecked()) {
            matchRequestBean.setSixMonthOverdueTimes(3);
        }
        if (checkBox2.isChecked()) {
            matchRequestBean.setYearOverdueTimesThreesTimes("1");
        } else {
            matchRequestBean.setYearOverdueTimesThreesTimes("0");
        }
        if (checkBox3.isChecked()) {
            matchRequestBean.setTwoYearOverdueFourTimes("1");
        } else {
            matchRequestBean.setTwoYearOverdueFourTimes("0");
        }


        if (checkBox4.isChecked()) {
            //没有选择的话默认全部没有
            matchRequestBean.setSixMonthOverdueTimes(0);
            matchRequestBean.setYearOverdueTimesThreesTimes("0");
            matchRequestBean.setTwoYearOverdueFourTimes("0");
        }
        matchRequestBean.setCompanyName(etQyName.getText().toString());
        matchRequestBean.setAge(Integer.parseInt(etAge.getText().toString().trim()));
        matchRequestBean.setCustomerName(etName.getText().toString().trim());
        Intent intent = new Intent(mContext, MatchSecondActivity.class);
        intent.putExtra("data", matchRequestBean);
        startActivity(intent);

    }


}
