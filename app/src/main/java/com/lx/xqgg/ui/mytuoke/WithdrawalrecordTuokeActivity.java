package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mytuoke.adapter.WithdrawAlrecordTuokeAdapter;
import com.lx.xqgg.ui.mytuoke.bean.WithdrawAlrecordTuokelistBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//提现记录--拓客
public class WithdrawalrecordTuokeActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.withdraw_alrecord_TuokeRecyclerView)
    RecyclerView withdrawAlrecordTuokeRecyclerView;
    @BindView(R.id.alltixjiftuoke)
    TextView alltixjiftuoke;
    @BindView(R.id.wudata)
    TextView wudata;
    private WithdrawAlrecordTuokeAdapter withdrawAlrecordTuokeAdapter;
    private List<WithdrawAlrecordTuokelistBean> withdrawAlrecordTuokelist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawalrecord_tuoke;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("提现记录");

    }

    @Override
    protected void initData() {
        withdrawAlrecordTuoke();
    }
    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
    //拓客提现记录列表
    private void withdrawAlrecordTuoke(){
        withdrawAlrecordTuokelist = new ArrayList<>();
        withdrawAlrecordTuokelist.add(new WithdrawAlrecordTuokelistBean("17312615026","100","2020-08-05 13:20","2020-08-06 17:20","7000","100","已到账"));
        withdrawAlrecordTuokelist.add(new WithdrawAlrecordTuokelistBean("17666615026","500","2020-11-05 19:20","2020-11-06 10:20","8000","500","处理中"));
        withdrawAlrecordTuokelist.add(new WithdrawAlrecordTuokelistBean("25555615026","900","2020-12-05 17:20","2020-12-06 18:20","2000","900","失败"));
        withdrawAlrecordTuokelist.add(new WithdrawAlrecordTuokelistBean("17312989898","1000","2021-01-05 13:20","2020-01-06 17:20","9000","1000","已到账"));
        withdrawAlrecordTuokeAdapter = new WithdrawAlrecordTuokeAdapter(withdrawAlrecordTuokelist);
        withdrawAlrecordTuokeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        withdrawAlrecordTuokeRecyclerView.setAdapter(withdrawAlrecordTuokeAdapter);

    }

}
