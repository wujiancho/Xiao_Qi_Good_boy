package com.lx.xqgg.ui.order;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.adapter.TjAdapter;
import com.lx.xqgg.ui.order.bean.OrderUserBean;
import com.lx.xqgg.ui.order.bean.TjBean;
import com.lx.xqgg.util.DensityUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//统计页面
public class TjActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.layout_hor)
    HorizontalScrollView layoutHor;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_this_month)
    RadioButton rbThisMonth;
    @BindView(R.id.rb_last_month)
    RadioButton rbLastMonth;
    @BindView(R.id.rb_customize)
    RadioButton rbCustomize;
    @BindView(R.id.rv_header)
    RecyclerView rvHeader;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.btn_fullscreen)
    Button btnFullscreen;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.btn_confirm_time)
    Button btnConfirmTime;
    @BindView(R.id.layout_select_time)
    LinearLayout layoutSelectTime;

    private String createTimeStart = "";
    private String createTimeEnd = "";

    private Calendar calendar;
    private List<OrderUserBean> orderUserBeanList = new ArrayList<>();
    private int userId = -1;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tj;
    }

    private TjAdapter tjAllAdapter;
    private List<TjBean> listAll = new ArrayList<>();
    private TjAdapter tjDetailAdapter;
    private List<TjBean> listDetail = new ArrayList<>();

    @Override
    protected void initView() {
        tvTitle.setText("业务统计");
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tjAllAdapter = new TjAdapter(R.layout.item_tj_all, listAll);
        rvHeader.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvHeader.setAdapter(tjAllAdapter);
        rvHeader.setNestedScrollingEnabled(false);

        layoutSelectTime.setVisibility(View.GONE);

        tjDetailAdapter = new TjAdapter(R.layout.item_tj_min_with_line, listDetail);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvContent.setAdapter(tjDetailAdapter);

        initUser();
        upDateTj();
    }

    @Override
    protected void initData() {

    }

    /**
     * 获取服务商下面的用户
     */
    private void initUser() {
        addSubscribe(ApiManage.getInstance().getMainApi().getUsers(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<OrderUserBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<OrderUserBean>> listBaseData) {
                        Log.e("test", new Gson().toJson(listBaseData));
                        orderUserBeanList.add(new OrderUserBean("-1", "全部"));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                layoutHor.setVisibility(View.VISIBLE);
                                orderUserBeanList.addAll(listBaseData.getData());
                            } else {
                                layoutHor.setVisibility(View.GONE);
                            }
                        } else {
                            layoutHor.setVisibility(View.GONE);
//                            toast("获取服务商用户失败");
                        }

                        for (OrderUserBean orderUserBean : orderUserBeanList) {
                            RadioButton button = new RadioButton(mContext);
                            /**第一个选中**/
//                            if (orderUserBean.getId().equals("-1")) {
//                                button.setChecked(true);
//                            }
                            setRaidBtnAttribute(button, orderUserBean.getName(), orderUserBean.getId());
                            radioGroup.addView(button);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button
                                    .getLayoutParams();
                            layoutParams.setMargins(0, 0, DensityUtils.dip2px(mContext, 12), 0);//4个参数按顺序分别是左上右下
                            button.setLayoutParams(layoutParams);

                        }
                    }
                }));
    }

    @SuppressLint("ResourceType")
    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, String id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setBackgroundResource(R.drawable.rb_user_selector);
        codeBtn.setTextColor(mContext.getResources().getColorStateList(R.drawable.txt_selector));
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        //codeBtn.setTextSize( ( textSize > 16 )?textSize:24 );
        codeBtn.setId(Integer.parseInt(id));
        codeBtn.setText(btnContent);
        codeBtn.setPadding(DensityUtils.dip2px(mContext, 12), DensityUtils.dip2px(mContext, 4), DensityUtils.dip2px(mContext, 12), DensityUtils.dip2px(mContext, 4));

        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新列表
                if(codeBtn.getText().equals("全部")){
                    userId=-1;
                }else {
                    userId = codeBtn.getId();
                }
                Log.e("test",userId+"");
                upDateTj();
            }
        });
        //DensityUtilHelps.Dp2Px(this,40)
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Log.e("zlz",userId+"");
        codeBtn.setLayoutParams(rlp);
    }

    @OnClick({R.id.rb_last_month, R.id.rb_this_month, R.id.rb_all, R.id.rb_customize, R.id.tv_start_time, R.id.tv_end_time, R.id.btn_confirm_time, R.id.v_close, R.id.btn_fullscreen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_last_month:
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
                upDateTj();
                break;
            case R.id.rb_this_month:
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
                upDateTj();
                break;
            case R.id.rb_all:
                layoutSelectTime.setVisibility(View.GONE);
                createTimeStart = "";
                createTimeEnd = "";
                upDateTj();
                break;
            case R.id.rb_customize:
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
                tvStartTime.setText(createTimeStart);
                tvEndTime.setText(createTimeEnd);
                layoutSelectTime.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_start_time:
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
                        tvStartTime.setText(createTimeStart);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_end_time:
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
                        tvEndTime.setText(createTimeEnd);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_confirm_time:
                try {
                    Date date1 = simpleDateFormat.parse(createTimeStart);
                    Date date2 = simpleDateFormat.parse(createTimeEnd);
                    if (date1.getTime() > date2.getTime()) {
                        toast("结束时间大于开始时间！");
                        return;
                    }
                    upDateTj();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_fullscreen:
                Intent intent = new Intent(mContext, TjFullScreenActivity.class);
                intent.putExtra("list1", (Serializable) listAll);
                intent.putExtra("list2", (Serializable) listDetail);
                startActivity(intent);
                break;
        }
    }

    private void upDateTj() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("dateStart", createTimeStart);
        paramsMap.put("dateEnd", createTimeEnd);
        paramsMap.put("userId", userId == -1 ? null : userId);
        Log.e("zlz", userId + "id");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getTj(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<TjBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<TjBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                List<TjBean> list1 = new ArrayList<>();
                                list1.add(listBaseData.getData().get(0));
                                listAll.clear();
                                listAll.addAll(list1);
                                Log.e("zlz", listAll.size() + "size");
                                tjAllAdapter.notifyDataSetChanged();


                                List<TjBean> list2 = listBaseData.getData();
                                Iterator<TjBean> iterator = list2.iterator();
                                while (iterator.hasNext()) {
                                    TjBean value = iterator.next();
                                    if (value.getId().equals("-1")) {
                                        iterator.remove();
                                    }
                                }
                                listDetail.clear();
                                listDetail.addAll(list2);
                                tjDetailAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast("没有统计数据！");
                            listAll.clear();
                            listDetail.clear();
                            tjAllAdapter.notifyDataSetChanged();
                            tjDetailAdapter.notifyDataSetChanged();
                        }
                    }
                }));
    }
}
