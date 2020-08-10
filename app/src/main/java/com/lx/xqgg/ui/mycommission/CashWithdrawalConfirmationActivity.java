package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//提现确认
public class CashWithdrawalConfirmationActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_withdrawal_confirmation;
    }

    @Override
    protected void initView() {
      tvTitle.setText("提现确认");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
