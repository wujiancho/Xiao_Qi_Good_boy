package com.lx.xqgg.ui.integral_query;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.integral_query.adapter.IntegralqueryHistorryAdapter;
import com.lx.xqgg.ui.integral_query.bean.IntegralqueryhistoryBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//历史记录--积分
public class Integral_queryhistoryActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.integral_count)
    TextView integralCount;
    @BindView(R.id.integral_txke)
    TextView integralTxke;
    @BindView(R.id.integral_historyrecyclerView)
    RecyclerView integralHistoryrecyclerView;
    private List<IntegralqueryhistoryBean> history;
    private IntegralqueryHistorryAdapter integralqueryHistorryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_queryhistory;

    }

    @Override
    protected void initView() {
        tvTitle.setText("历史积分提现记录");
    }

    @Override
    protected void initData() {
     history=new ArrayList<>();
     history.add(new IntegralqueryhistoryBean("2020.06.08","1000",0));
     history.add(new IntegralqueryhistoryBean("2020.06.08","1000",1));
     history.add(new IntegralqueryhistoryBean("2020.06.08","1000",0));
     history.add(new IntegralqueryhistoryBean("2020.06.08","1000",1));
        integralqueryHistorryAdapter = new IntegralqueryHistorryAdapter(history);
        integralHistoryrecyclerView.setAdapter(integralqueryHistorryAdapter);
     integralHistoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

}
