package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

//提现记录--拓客
public class WithdrawalrecordTuokeActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.withdraw_alrecord_TuokeRecyclerView)
    RecyclerView withdrawAlrecordTuokeRecyclerView;

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

    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
}
