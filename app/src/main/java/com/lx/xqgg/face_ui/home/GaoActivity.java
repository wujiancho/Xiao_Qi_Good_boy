package com.lx.xqgg.face_ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class GaoActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.btn_apply)
    Button btnApply;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gao;
    }

    @Override
    protected void initView() {
        tvTitle.setText("高参汇");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.btn_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_apply:
                startActivity(new Intent(mContext,GaoEnrollActivity.class));
                break;
        }
    }
}
