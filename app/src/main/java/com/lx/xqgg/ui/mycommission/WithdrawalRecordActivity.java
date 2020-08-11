package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.adapter.WithdrawalrecordAdapter;
import com.lx.xqgg.ui.mycommission.bean.WithdrawalrecordBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//提现记录
public class WithdrawalRecordActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.accumulative_withdrawal)
    TextView accumulativeWithdrawal;
    @BindView(R.id.withdrawal_recordRecyclerView)
    RecyclerView withdrawalRecordRecyclerView;
    private List<WithdrawalrecordBean> withdrawalrecordlist;
    private WithdrawalrecordAdapter withdrawalrecordAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    protected void initView() {
       toobarTitle.setText("提现记录");
    }

    @Override
    protected void initData() {
        withdrawalrecordlist=new ArrayList<>();
        withdrawalrecordlist.add(new WithdrawalrecordBean("2020.06.08","0208","1,000",1));
        withdrawalrecordlist.add(new WithdrawalrecordBean("2020.06.08","0208","1,000",2));
        withdrawalrecordlist.add(new WithdrawalrecordBean("2020.06.08","0208","1,000",0));
        withdrawalrecordAdapter = new WithdrawalrecordAdapter(withdrawalrecordlist);
        withdrawalRecordRecyclerView.setAdapter(withdrawalrecordAdapter);
        withdrawalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
}
