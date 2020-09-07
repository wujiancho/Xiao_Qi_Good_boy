package com.lx.xqgg.ui.mytuoke;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.integral_query.adapter.Message_Adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

//我的拓客首页
public class MyTuokeActivity extends BaseActivity {
    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.user_nametuoke)
    TextView userNametuoke;
    @BindView(R.id.usercompany_nametuoke)
    TextView usercompanyNametuoke;
    @BindView(R.id.accumulated_pointstuoke1)
    TextView accumulatedPointstuoke1;
    @BindView(R.id.cash_withdrawal_rebatetuoke)
    TextView cashWithdrawalRebatetuoke;
    @BindView(R.id.cash_withdrawal_rebatetuokez)
    LinearLayout cashWithdrawalRebatetuokez;
    @BindView(R.id.this_monthcommissiontuoke)
    TextView thisMonthcommissiontuoke;
    @BindView(R.id.this_monthcommissiontuokez)
    LinearLayout thisMonthcommissiontuokez;
    @BindView(R.id.accumulated_rebatetuoke)
    TextView accumulatedRebatetuoke;
    @BindView(R.id.accumulated_rebatetuoketuokez)
    LinearLayout accumulatedRebatetuoketuokez;
    @BindView(R.id.accumulated_pointstuoke)
    TextView accumulatedPointstuoke;
    @BindView(R.id.rb_alltuoke)
    RadioButton rbAlltuoke;
    @BindView(R.id.rb_this_monthtuoke)
    RadioButton rbThisMonthtuoke;
    @BindView(R.id.rb_last_monthtuoke)
    RadioButton rbLastMonthtuoke;
    @BindView(R.id.rb_customizetuoke)
    RadioButton rbCustomizetuoke;
    @BindView(R.id.tv_start_timetuoke)
    TextView tvStartTimetuoke;
    @BindView(R.id.tv_end_timetuoke)
    TextView tvEndTimetuoke;
    @BindView(R.id.btn_confirm_timetuoke)
    Button btnConfirmTimetuoke;
    @BindView(R.id.layout_select_time)
    LinearLayout layoutSelectTime;
    @BindView(R.id.message_recyclerviewtuoke)
    RecyclerView messageRecyclerviewtuoke;
    private Message_Adapter message_adapter;
    private List<String> message;
    private String createTimeStart = "";
    private String createTimeEnd = "";
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_tuoke;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("我的拓客");
    }

    @Override
    protected void initData() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        message = new ArrayList<>();
        message.add("2020.06.09您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得500积分。");
        message.add("2020.06.10您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得600积分。");
        message.add("2020.06.11您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得1000积分。");
        message.add("2020.06.12您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得800积分。");
        message.add("2020.06.13您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得400积分。");
        message.add("2020.06.13您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得400积分。");
        message.add("2020.06.13您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得400积分。");
        message_adapter = new Message_Adapter(message);
        messageRecyclerviewtuoke.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        messageRecyclerviewtuoke.setAdapter(message_adapter);
    }


    @OnClick({R.id.toobar_back, R.id.tv_start_timetuoke, R.id.tv_end_timetuoke,R.id.cash_withdrawal_rebatetuokez, R.id.this_monthcommissiontuokez, R.id.accumulated_rebatetuoketuokez, R.id.rb_alltuoke, R.id.rb_this_monthtuoke, R.id.rb_last_monthtuoke, R.id.rb_customizetuoke, R.id.btn_confirm_timetuoke})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            //跳转可提现
            case R.id.cash_withdrawal_rebatetuokez:
                Intent intentdltuoke = new Intent(MyTuokeActivity.this, DerivableintegralTuokeActivity.class);
                startActivity(intentdltuoke);
                break;
            //跳转本月积分
            case R.id.this_monthcommissiontuokez:
                break;
            //跳转累计拓客
            case R.id.accumulated_rebatetuoketuokez:
                Intent intentcldtuoke = new Intent(MyTuokeActivity.this, CumulativedevelopmentActivity.class);
                startActivity(intentcldtuoke);
                break;
            //今年
            case R.id.rb_alltuoke:
                layoutSelectTime.setVisibility(View.GONE);
                createTimeStart = "";
                createTimeEnd = "";
                break;
            //本月
            case R.id.rb_this_monthtuoke:
                layoutSelectTime.setVisibility(View.GONE);
                layoutSelectTime.setVisibility(View.GONE);
                int year1 = calendar.get(Calendar.YEAR);
                int month1 = calendar.get(Calendar.MONTH) + 1;
                if (month1 < 10) {
                    createTimeStart = year1 + "-0" + month1 + "-0" + 1;
                    createTimeEnd = year1 + "-0" + month1 + "-" + 31;
                } else {
                    createTimeStart = year1 + "-" + month1 + "-0" + 1;
                    createTimeEnd = year1 + "-" + month1 + "-" + 31;
                }
                break;
            //上月
            case R.id.rb_last_monthtuoke:
                layoutSelectTime.setVisibility(View.GONE);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                if (month == 0) {
                    month = 12;
                }
                if (month < 10) {
                    createTimeStart = year + "-0" + month + "-0" + 1;
                    createTimeEnd = year + "-0" + month + "-" + 31;
                } else {
                    createTimeStart = year + "-" + month + "-0" + 1;
                    createTimeEnd = year + "-" + month + "-" + 31;
                }
                break;
            //自定义
            case R.id.rb_customizetuoke:
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DATE);

                String index = "";
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        index = new StringBuffer().append(mYear)
                                .append("-").append("0")
                                .append(mMonth + 1)
                                .append("-").append("0")
                                .append(mDay).toString();
                    } else {
                        index = new StringBuffer().append(mYear)
                                .append("-").append("0")
                                .append(mMonth + 1)
                                .append("-")
                                .append(mDay).toString();
                    }
                } else {
                    if (mDay < 10) {
                        index = new StringBuffer().append(mYear)
                                .append("-").append(mMonth + 1)
                                .append("-").append("0")
                                .append(mDay)
                                .toString();
                    } else {
                        index = new StringBuffer().append(mYear)
                                .append("-").append(mMonth + 1)
                                .append("-")
                                .append(mDay)
                                .toString();
                    }
                }
                createTimeStart = index;
                createTimeEnd = index;
                tvStartTimetuoke.setText(createTimeStart);
                tvEndTimetuoke.setText(createTimeEnd);
                layoutSelectTime.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_start_timetuoke:
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mYear = year;
                        int mMonth = month;
                        int mDay = dayOfMonth;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                createTimeStart = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(dayOfMonth).toString();
                            } else {
                                createTimeStart = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-")
                                        .append(dayOfMonth).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                createTimeStart = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(dayOfMonth)
                                        .toString();
                            } else {
                                createTimeStart = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-")
                                        .append(dayOfMonth)
                                        .toString();
                            }
                        }
                        tvStartTimetuoke.setText(createTimeStart);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_end_timetuoke:
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mYear = year;
                        int mMonth = month;
                        int mDay = dayOfMonth;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                createTimeEnd = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(dayOfMonth).toString();
                            } else {
                                createTimeEnd = new StringBuffer().append(mYear)
                                        .append("-").append("0")
                                        .append(mMonth + 1)
                                        .append("-")
                                        .append(dayOfMonth).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                createTimeEnd = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-").append("0")
                                        .append(dayOfMonth)
                                        .toString();
                            } else {
                                createTimeEnd = new StringBuffer().append(mYear)
                                        .append("-").append(mMonth + 1)
                                        .append("-")
                                        .append(dayOfMonth)
                                        .toString();
                            }
                        }
                        tvEndTimetuoke.setText(createTimeEnd);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            //自定义确定
            case R.id.btn_confirm_timetuoke:
                try {
                    Date date1 = simpleDateFormat.parse(createTimeStart);
                    Date date2 = simpleDateFormat.parse(createTimeEnd);
                    if (date1.getTime() > date2.getTime()) {
                        toast("结束时间大于开始时间！");
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
