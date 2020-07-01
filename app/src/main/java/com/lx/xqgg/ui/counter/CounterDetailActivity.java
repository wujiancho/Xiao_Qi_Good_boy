package com.lx.xqgg.ui.counter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.counter.adapter.CounterAdapter;
import com.lx.xqgg.ui.counter.bean.CountBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CounterDetailActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    /**
     * 贷款总金额
     **/
    private double money;
    /**
     * 贷款月
     **/
    private int month;
    /**
     * 利率
     **/
    private double rate;
    /**
     * 还款类型 1 等额本金  2 等额本息
     **/
    private int type;

    private double hkNum;
    private double hkLx;

    private double ygbj;

    private List<CountBean> list = new ArrayList<>();
    private CounterAdapter counterAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_counter_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人贷款明细");
        money = getIntent().getDoubleExtra("money", 0);
        month = getIntent().getIntExtra("year", 0) * 12;
        rate = getIntent().getDoubleExtra("rate", 0);
        type = getIntent().getIntExtra("type", 0);
        Log.e("zlz", "money" + money + "month" + month + "rate" + rate);
        counterAdapter = new CounterAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(counterAdapter);
    }

    @Override
    protected void initData() {
        /**
         * 等额本金
         */
        if (type == 1) {
            hkNum = (month + 1) * money * (Double.valueOf(rate) / 2400.00) + money;
            hkLx = (month + 1) * money * (Double.valueOf(rate) / 2400.00);
            ygbj = Double.valueOf(money) / Double.valueOf(month);

            Double sybj = Double.valueOf(money);

            double firstLx = money * (Double.valueOf(rate) / 1200.00);
            double firstYg = ygbj + firstLx;
            /**利息减少**/
            double lxjs = ygbj * (Double.valueOf(rate) / 1200.00);

            CountBean allBean = new CountBean("全部", hkNum, money, hkLx, 0.00);
            for (int i = 0; i < month; i++) {
                if (i == 0) {
                    hkNum = hkNum - firstYg;
                    sybj = sybj - ygbj;
                    list.add(new CountBean((i + 1) + "", firstYg, ygbj, firstLx, sybj));
                } else {
                    firstYg = firstYg - lxjs;
                    firstLx = firstLx - lxjs;
                    hkNum = hkNum - firstYg;
                    sybj = sybj - ygbj;
                    list.add(new CountBean((i + 1) + "", firstYg, ygbj, firstLx, sybj));
                }

            }
            list.add(allBean);
            counterAdapter.notifyDataSetChanged();
        } else {
            Double sybj = Double.valueOf(money);
            double bj = Double.parseDouble(money + "");
            double yg = bj * (rate / 1200) * Math.pow((1 + (rate / 1200)), month) / (Math.pow(1 + (rate / 1200), month) - 1);
            hkNum = yg * month;
            CountBean allBean = new CountBean("全部", hkNum, money, hkNum - money, 0.00);
            for (int i = 0; i < month; i++) {
                double mylx = bj * (rate / 1200);
                hkNum = hkNum - yg;
                sybj = sybj - yg + mylx;
                bj = bj - (yg - mylx);
                list.add(new CountBean((i + 1) + "", yg, yg - mylx, mylx, sybj));
            }
            list.add(allBean);
            counterAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
