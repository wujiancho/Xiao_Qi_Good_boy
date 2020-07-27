package com.lx.xqgg.ui.integral_query;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.integral_query.adapter.Message_Adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//积分查询
public class IntegralQueryActivity extends BaseActivity {


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
    @BindView(R.id.integral_queryhistory)
    TextView integralQueryhistory;
    @BindView(R.id.integral_tx)
    LinearLayout integralTx;
    @BindView(R.id.message_recyclerview)
    RecyclerView messageRecyclerview;
    private Message_Adapter message_adapter;
    private List<String> message;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_query;
    }

    @Override
    protected void initView() {
        tvTitle.setText("积分查询");
    }

    @Override
    protected void initData() {
        message = new ArrayList<>();
        message.add("2020.06.09您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得500积分。");
        message.add("2020.06.10您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得600积分。");
        message.add("2020.06.11您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得1000积分。");
        message.add("2020.06.12您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得800积分。");
        message.add("2020.06.13您开拓的服务商苏州罗信网络科技有限公司完成一笔新的订单，获得400积分。");
        message_adapter = new Message_Adapter(message);
        messageRecyclerview.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        messageRecyclerview.setAdapter(message_adapter);
    }


    @OnClick({R.id.v_close, R.id.integral_queryhistory, R.id.integral_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.integral_queryhistory://历史记录
                Intent intenthistory = new Intent(IntegralQueryActivity.this, Integral_queryhistoryActivity.class);
                startActivity(intenthistory);
                break;
            case R.id.integral_tx://积分提现
                Intent intentwithdrawal = new Intent(IntegralQueryActivity.this, IntegralWithdrawalActivity.class);
                startActivity(intentwithdrawal);
                break;
        }
    }

}
