package com.lx.xqgg.ui.integral_query;

import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.adapter.WithdrawalrecordAdapter;
import com.lx.xqgg.ui.mycommission.bean.WithdrawalrecordBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
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

    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

}
