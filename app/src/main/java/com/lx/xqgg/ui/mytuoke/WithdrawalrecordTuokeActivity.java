package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mytuoke.adapter.WithdrawAlrecordTuokeAdapter;
import com.lx.xqgg.ui.mytuoke.bean.WithdrawAlrecordTuokelistBean;

import java.util.ArrayList;

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
    private ArrayList<WithdrawAlrecordTuokelistBean> withdrawAlrecordTuokelist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawalrecord_tuoke;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("提现记录");
        withdrawAlrecordTuokelist = new ArrayList<>();
        withdrawAlrecordTuokeAdapter = new WithdrawAlrecordTuokeAdapter(withdrawAlrecordTuokelist);
        withdrawAlrecordTuokeRecyclerView.setAdapter(withdrawAlrecordTuokeAdapter);
        withdrawAlrecordTuokeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }

}
